package com.klpc.stadspring.domain.advertVideo.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ModifyVideoRequest {

    private Long videoId;
    private MultipartFile video;

}
