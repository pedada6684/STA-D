package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UpdateUserInfoCommand {
    private Long userId;
    private String name;
    private String nickname;
    private String phone;
    private String company;
    private String comNo;
    private String department;
    private String password;
    private MultipartFile profile;

    public UpdateProfileImgCommand convertToUPICommand(){
        return UpdateProfileImgCommand.builder()
                .userId(userId)
                .profile(profile)
                .build();
    }
}
