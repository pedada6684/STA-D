package com.klpc.stadspring.domain.user.service;

import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.entity.UserLocation;
import com.klpc.stadspring.domain.user.repository.UserLocationRepository;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.domain.user.service.command.*;
import com.klpc.stadspring.global.auth.controller.response.LoginResult;
import com.klpc.stadspring.global.auth.jwt.AuthTokenGenerator;
import com.klpc.stadspring.global.auth.service.RefreshTokenService;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import com.klpc.stadspring.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;
    private final AuthTokenGenerator authTokenGenerator;
    private final RefreshTokenService refreshTokenService;
    private final S3Util s3Util;


    public User findUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return user;
    }

    /**
     * 프로필 사진 변경 메서드
     * @param command userId, File
     * @return S3 url
     */
    @Transactional(readOnly = false)
    public String updateProfileImg(UpdateProfileImgCommand command) {
        log.info("UpdateProfileImgCommand: "+command);
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        URL S3Url = s3Util.uploadImageToS3(command.getProfile(), "user_profile", user.getId().toString());
        Objects.requireNonNull(S3Url);
        user.updateProfileUrl(S3Url.toString());
        return S3Url.toString();
    }

    @Transactional(readOnly = false)
    public void withdrawUser(WithdrawUserCommand command) {
        log.info("WithdrawUserCommand: "+command);
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        user.withdraw();
        return;
    }

    @Transactional(readOnly = false)
    public User updateUserInfo(UpdateUserInfoCommand command) {
        log.info("UpdateUserInfoCommand: "+command);
        User user = findUserById(command.getUserId());
        if (command.getComNo() != null //회사 회원
                && !command.getComNo().isEmpty()
                && !user.getPassword().equals(command.getPassword())
        ){
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        updateProfileImg(command.convertToUPICommand());
        user.update(command);
        return user;
    }
    @Transactional(readOnly = false)
    public LoginResult JoinCompanyUser(JoinCompanyUserCommand command) {
        log.info("JoinCompanyUserCommand: "+command);
        if (userRepository.existsByEmail(command.getEmail())){
            throw new CustomException(ErrorCode.ENTITIY_ALREADY_EXIST);
        }
        //회원 생성
        User newUser = User.createNewUser(
                command.getEmail(),
                command.getPassword(),
                command.getName(),
                command.getName(),
                2L
        );
        newUser.update(command.convertToUpdateCommand());
        // 회원가입
        userRepository.save(newUser);
        //로그인
        WebLoginCommand webLoginCommand = WebLoginCommand.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .build();
        return webLogin(webLoginCommand);
    }
    public LoginResult webLogin(WebLoginCommand command) {
        log.info("WebLoginCommand: "+command);
        User loginUser = userRepository.findByEmailAndPassword(command.getEmail(), command.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        LoginResult result = LoginResult.builder()
                .accessToken(authTokenGenerator.generateAT(loginUser.getId()))
                .refreshToken(authTokenGenerator.generateRT(loginUser.getId()))
                .build();
        return result;
    }
    @Transactional(readOnly = false)
    public LoginResult appLogin(AppLoginCommand command) {
        log.info("AppLoginCommand: "+command);
        User user = userRepository.findByEmail(command.getEmail())
                .orElseGet(// 신규 유저인 경우 회원 가입
                        ()->joinMember(command.convertToJoinUserCommand())
                );


        LoginResult result = LoginResult.builder()
                .accessToken(authTokenGenerator.generateAT(user.getId()))
                .refreshToken(authTokenGenerator.generateRT(user.getId()))
                .build();
        return result;
    }

    @Transactional(readOnly = false)
    public User joinMember(JoinUserCommand command) {
        log.info("JoinMemberCommand: "+command);
        //유저 회원가입
        User newMember = User.createNewUser(
                command.getEmail(),
                null,
                command.getNickname(),
                command.getName(),
                1L
        );
        newMember = userRepository.save(newMember);
        //유저 프로필 사진 저장
        URL S3Url = s3Util.uploadImageToS3(command.getProfileImage(), "profile", newMember.getId().toString());
        Objects.requireNonNull(S3Url);
        newMember.updateProfileUrl(S3Url.toString());
        //유저 구독채널 저장
        String youtubeInfo = getUserYoutubeInfo(command.getGoogleAT());
        newMember.updateYoutubeInfo(youtubeInfo);

        log.info("youtubeInfo: "+youtubeInfo);
        return newMember;
    }

    private String getUserYoutubeInfo(String googleAT) {
        //TODO: 유튜브에 api통신을 통해 채널을 가져오고 pasing해 하나의 스트링으로 만드는 코드를 만들어주길 부탁
        String concern = "축산물";
        BufferedReader in = null;
        try {
            URL url = new URL("https://youtube.googleapis.com/youtube/v3/subscriptions?part=snippet,contentDetails&mine=true&maxResults=50");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + googleAT);

            int responseCode = con.getResponseCode();

            log.info("responseCode22222222222222222222222222: "+responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { //성공
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                try {
                    // JSON 문자열을 JSONObject로 파싱
                    JSONObject jsonObject = new JSONObject(response.toString());
                    // "items" 배열 가져오기
                    JSONArray itemsArray = jsonObject.getJSONArray("items");

                    // "items" 배열 안의 각 객체의 "snippet" 안의 "description" 값을 한 줄로 모아서 저장할 문자열
                    StringBuilder descriptions = new StringBuilder();

                    if (itemsArray.length() != 0) {
                        // "items" 배열 안의 각 객체에 대해 반복
                        for (int i = 0; i < itemsArray.length(); i++) {
                            JSONObject item = itemsArray.getJSONObject(i);
                            JSONObject snippet = item.getJSONObject("snippet");
                            // "description" 값을 가져와서 descriptions에 추가
                            descriptions.append(snippet.getString("description").replaceAll("\\n", " "));
                            // 마지막 객체인 경우 줄바꿈 없이 추가
                            if (i < itemsArray.length() - 1) {
                                descriptions.append(" ");
                            }
                        }
                        concern = descriptions.toString();

                        log.info("concern :"+ concern);
                    }
                } catch (JSONException e) {
                    log.info("errorerrorerrorerror");
                    e.printStackTrace();
                }

            } else { // 에러 발생
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return concern;
    }

    public void logout(LogoutCommand command) {
        log.info("LogoutCommand: "+command);
        refreshTokenService.removeRefreshToken(command.getUserId());
        return;
    }

    @Transactional(readOnly = false)
    public UserLocation createUserLocation(CreateUserLocationCommand command) {
        log.info("CreateUserLocationCommand: "+command);
        User user = findUserById(command.getUserId());
        UserLocation newUserLocation = UserLocation.createNewUserLocation(
                user,
                command.getLocation(),
                command.getName(),
                command.getPhone(),
                command.getLocationNick()
        );
        user.addUserLocation(newUserLocation);
        return newUserLocation;
    }

    @Transactional(readOnly = false)
    public UserLocation updateUserLocation(UpdateUserLocationCommand command) {
        log.info("UpdateUserLocationCommand: "+command);
        User user = findUserById(command.getUserId());
        return user.updateUserLocation(command);
    }

    @Transactional(readOnly = false)
    public void deleteUserLocation(DeleteUserLocationCommand command) {
        log.info("DeleteUserLocationCommand: "+command);
        long cnt = userLocationRepository.deleteByIdAndUser_Id(command.getLocationId(), command.getUserId());
        if (cnt == 0){ //삭제할 수 있는 내주소지 없음
            throw new CustomException(ErrorCode.ENTITIY_NOT_FOUND);
        }
    }

    public List<UserLocation> getUserLocation(GetUserLocationCommand command) {
        log.info("GetUserLocationCommand: "+command);
        return userLocationRepository.findAllByUser_Id(command.getUserId());
    }
}
