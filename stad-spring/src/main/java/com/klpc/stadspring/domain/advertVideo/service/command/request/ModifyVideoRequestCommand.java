package com.klpc.stadspring.domain.advertVideo.service.command.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ModifyVideoRequestCommand {

    Long videoId;
    MultipartFile video;

}
