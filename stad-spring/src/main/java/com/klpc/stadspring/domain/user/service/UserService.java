package com.klpc.stadspring.domain.user.service;

import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.entity.UserYoutubeInfo;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
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

    public void withdrawUser(WithdrawUserCommand command) {
        log.info("WithdrawUserCommand: "+command);
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        user.withdraw();
        return;
    }

    public User updateUserInfo(UpdateUserInfoCommand command) {
        log.info("UpdateUserInfoCommand: "+command);
        User user = findUserById(command.getUserId());
        if (command.getPassword() != null //기업 정보 변경인 경우
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
        return newMember;
    }

    private String getUserYoutubeInfo(String googleAT) {
        //TODO: 유튜브에 api통신을 통해 채널을 가져오고 pasing해 하나의 스트링으로 만드는 코드를 만들어주길 부탁
        return null;
    }

    public void logout(LogoutCommand command) {
        log.info("LogoutCommand: "+command);
        refreshTokenService.removeRefreshToken(command.getUserId());
        return;
    }
}