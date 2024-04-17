package com.klpc.stadspring.domain.example.controller;

import com.klpc.stadspring.domain.example.controller.request.UpdateProfileImgRequest;
import com.klpc.stadspring.domain.example.controller.response.GetMemberInfoResponse;
import com.klpc.stadspring.domain.example.controller.response.UpdateProfileResponse;
import com.klpc.stadspring.domain.example.entity.Member;
import com.klpc.stadspring.domain.example.service.MemberService;
import com.klpc.stadspring.domain.example.service.command.FindMemberByIdCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "example 컨트롤러", description = "예시) 사용자 API 입니다.")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/profile")
    @Operation(summary = "예시) 프로필 이미지 변경", description = "예시) 프로필 이미지 변경")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class)))
    public ResponseEntity<UpdateProfileResponse> updateProfileImg(@ModelAttribute UpdateProfileImgRequest request) {
        log.info("UpdateProfileRequest: " + request);
        //유저 아이디 검증 메서드 하나 추가해야함 with jwt
        String profileImgUrl = memberService.updateProfileImg(request.toCommand());

        UpdateProfileResponse response = UpdateProfileResponse.builder()
            .profileImgUrl(profileImgUrl)
            .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping
    @Operation(summary = "예시) 유저 정보 요청", description = "예시) 유저 정보 요청")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GetMemberInfoResponse.class)))
    public ResponseEntity<GetMemberInfoResponse> getMemberInfo(@RequestParam("memberId") Long memberId) {
        FindMemberByIdCommand command = FindMemberByIdCommand.builder()
                .id(memberId)
                .build();
        Member member = memberService.findMemberById(command);
        GetMemberInfoResponse response = GetMemberInfoResponse.from(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
