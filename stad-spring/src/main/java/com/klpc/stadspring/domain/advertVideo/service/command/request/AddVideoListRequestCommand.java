package com.klpc.stadspring.domain.advertVideo.service.command.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class AddVideoListRequestCommand {
    
    public List<MultipartFile> list;
    
}
