package com.klpc.stadspring.domain.user.controller.request;

import com.klpc.stadspring.domain.user.service.command.UpdateProfileImgCommand;
import com.klpc.stadspring.domain.user.service.command.UpdateUserInfoCommand;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdateUserInfoRequest {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String company;
    private String department;
    private String comNo;

    public UpdateUserInfoCommand toCommand(){
        return UpdateUserInfoCommand.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .nickname(nickname)
                .name(name)
                .company(company)
                .department(department)
                .comNo(comNo)
                .build();
    }
}
