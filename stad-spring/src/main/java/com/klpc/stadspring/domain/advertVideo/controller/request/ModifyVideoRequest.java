package com.klpc.stadspring.domain.advertVideo.controller.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ModifyVideoRequest {

    Long videoId;
    MultipartFile video;

}
