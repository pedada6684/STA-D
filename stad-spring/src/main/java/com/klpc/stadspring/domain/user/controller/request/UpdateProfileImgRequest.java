package com.klpc.stadspring.domain.user.controller.request;

import com.klpc.stadspring.domain.user.service.command.UpdateProfileImgCommand;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UpdateProfileImgRequest {
    Long memberId;
    MultipartFile profileImg;
    public UpdateProfileImgCommand toCommand(){
        return UpdateProfileImgCommand.builder()
                .memberId(memberId)
                .profileImg(profileImg)
                .build();
    }
}
