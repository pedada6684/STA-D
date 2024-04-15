package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UpdateProfileImgCommand {
    Long memberId;
    MultipartFile profileImg;
}
