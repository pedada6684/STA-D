package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UpdateUserInfoCommand {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String company;
    private String department;
    private String comNo;
}
