package com.klpc.stadspring.domain.advertVideo.controller.request;

import com.klpc.stadspring.domain.advertVideo.service.command.request.AddVideoListRequestCommand;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class AddVideoListRequest {

    List<MultipartFile> videoList;

}
