package com.klpc.stadspring.domain.example.service;

import com.klpc.stadspring.domain.example.entity.Member;
import com.klpc.stadspring.domain.example.repository.MemberRepository;
import com.klpc.stadspring.domain.example.service.command.FindMemberByIdCommand;
import com.klpc.stadspring.domain.example.service.command.UpdateProfileImgCommand;
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
public class MemberService {
    private final MemberRepository memberRepository;
    private final S3Util s3Util;


    public Member findMemberById(FindMemberByIdCommand command){
        Member member = memberRepository.findById(command.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return member;
    }


  /**
   * 프로필 사진 변경 메서드
   * @param command userId, File
   * @return S3 url
   */
    @Transactional(readOnly = false)
    public String updateProfileImg(UpdateProfileImgCommand command) {
        log.info("UpdateProfileImgCommand: "+command);
        Member member = memberRepository.findById(command.getMemberId())
              .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        URL S3Url = s3Util.uploadImageToS3(command.getProfileImg(), "profile", member.getId().toString());
        Objects.requireNonNull(S3Url);
        return S3Url.toString();
    }
}