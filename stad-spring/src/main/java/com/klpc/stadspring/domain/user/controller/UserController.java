package com.klpc.stadspring.domain.user.controller;

import com.klpc.stadspring.domain.user.controller.request.CreateUserLocationRequest;
import com.klpc.stadspring.domain.user.controller.request.UpdateProfileImgRequest;
import com.klpc.stadspring.domain.user.controller.request.UpdateUserInfoRequest;
import com.klpc.stadspring.domain.user.controller.request.UpdateUserLocationRequest;
import com.klpc.stadspring.domain.user.controller.response.CreateUserLocationResponse;
import com.klpc.stadspring.domain.user.controller.response.GetUserInfoResponse;
import com.klpc.stadspring.domain.user.controller.response.UpdateProfileResponse;
import com.klpc.stadspring.domain.user.controller.response.UpdateUserLocationResponse;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.entity.UserLocation;
import com.klpc.stadspring.domain.user.service.UserService;
import com.klpc.stadspring.domain.user.service.command.WithdrawUserCommand;
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
@RequestMapping("/user")
@Tag(name = "user 컨트롤러", description = "사용자 API 입니다.")
public class UserController {

    private final UserService userService;

    @GetMapping("/withdraw")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴")
    @ApiResponse(responseCode = "200", description = "회원탈퇴가 성공적으로 진행되었습니다.")
    public ResponseEntity<?> withdrawUser(@RequestParam("userId") Long userId) {
        WithdrawUserCommand command = WithdrawUserCommand.builder()
                .userId(userId)
                .build();
        userService.withdrawUser(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    @Operation(summary = "유저 정보 요청", description = "유저 정보 요청")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GetUserInfoResponse.class)))
    public ResponseEntity<GetUserInfoResponse> getUserInfo(@RequestParam("userId") Long userId) {
        User user = userService.findUserById(userId);
        GetUserInfoResponse response = GetUserInfoResponse.from(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update")
    @Operation(summary = "유저 정보 변경", description = "유저 정보 변경")
    @ApiResponse(responseCode = "200", description = "유저 정보 수정이 성공적으로 진행되었습니다.")
    public ResponseEntity<GetUserInfoResponse> updateUserInfo(@ModelAttribute UpdateUserInfoRequest request) {
        log.info("UpdateUserInfoRequest: " + request);
        User user = userService.updateUserInfo(request.toCommand());
        GetUserInfoResponse response = GetUserInfoResponse.from(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/profile")
    @Operation(summary = "프로필 이미지 변경", description = "프로필 이미지 변경")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class)))
    public ResponseEntity<UpdateProfileResponse> updateProfileImg(@ModelAttribute UpdateProfileImgRequest request) {
        log.info("UpdateProfileRequest: " + request);
        //유저 아이디 검증 메서드 하나 추가해야함 with jwt
        String profileImgUrl = userService.updateProfileImg(request.toCommand());

        UpdateProfileResponse response = UpdateProfileResponse.builder()
                .profileImgUrl(profileImgUrl)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/location")
    @Operation(summary = "유저 배송지 추가", description = "유저 배송지 추가")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class)))
    public ResponseEntity<CreateUserLocationResponse> createUserLocation(@RequestBody CreateUserLocationRequest request) {
        log.info("CreateUserLocationRequest: " + request);
        UserLocation userLocation = userService.createUserLocation(request.toCommand());
        CreateUserLocationResponse response = CreateUserLocationResponse.from(userLocation);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/location")
    @Operation(summary = "유저 배송지 수정", description = "유저 배송지 수정")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class)))
    public ResponseEntity<UpdateUserLocationResponse> updateUserLocation(@RequestBody UpdateUserLocationRequest request) {
        log.info("CreateUserLocationRequest: " + request);
        UserLocation userLocation = userService.updateUserLocation(request.toCommand());
        UpdateUserLocationResponse response = UpdateUserLocationResponse.from(userLocation);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
