package com.klpc.stadspring.domain.user.service;

import com.klpc.stadspring.domain.user.entity.User;
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


    public User findUserById(FindUserByIdCommand command){
        User user = userRepository.findById(command.getId())
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
        User user = userRepository.findById(command.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        URL S3Url = s3Util.uploadImageToS3(command.getProfileImg(), "user_profile", user.getId().toString());
        Objects.requireNonNull(S3Url);
        return S3Url.toString();
    }

    public void withdrawUser(WithdrawUserCommand command) {
        log.info("WithdrawUserCommand: "+command);
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        user.withdraw();
        return;
    }

    public void updateUserInfo(UpdateUserInfoCommand command) {
        log.info("UpdateUserInfoCommand: "+command);
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        user.update(command);
        return;
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
        User newMember = User.createNewUser(
                command.getEmail(),
                null,
                command.getNickname(),
                command.getName(),
                1L
      );
      newMember = userRepository.save(newMember);
      //트랜젝션 유의
      URL S3Url = s3Util.uploadImageToS3(command.getProfileImage(), "profile", newMember.getId().toString());
      Objects.requireNonNull(S3Url);
      newMember.updateProfileUrl(S3Url.toString());
      return newMember;
    }

    public void logout(LogoutCommand command) {
        log.info("LogoutCommand: "+command);
        refreshTokenService.removeRefreshToken(command.getUserId());
        return;
    }
}