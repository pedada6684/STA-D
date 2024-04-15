package com.klpc.stadspring.domain.user.service;

import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.domain.user.service.command.FindMemberByIdCommand;
import com.klpc.stadspring.domain.user.service.command.UpdateProfileImgCommand;
import com.klpc.stadspring.domain.user.service.command.WithdrawUserCommand;
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
  private final S3Util s3Util;


  public User findMemberById(FindMemberByIdCommand command){
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
}