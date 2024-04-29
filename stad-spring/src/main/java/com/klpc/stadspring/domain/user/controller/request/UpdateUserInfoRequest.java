package com.klpc.stadspring.domain.user.controller.request;

import com.klpc.stadspring.domain.user.service.command.UpdateUserInfoCommand;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UpdateUserInfoRequest {
    private Long userId;
    private String name;
    private String nickname;
    private String phone;
    private String company;
    private String comNo;
    private String department;
    private String password;
    private MultipartFile profile;

    public UpdateUserInfoCommand toCommand(){
        return UpdateUserInfoCommand.builder()
                .userId(userId)
                .name(name)
                .nickname(nickname)
                .phone(phone)
                .company(company)
                .comNo(comNo)
                .department(department)
                .password(password)
                .profile(profile)
                .build();
    }
}
