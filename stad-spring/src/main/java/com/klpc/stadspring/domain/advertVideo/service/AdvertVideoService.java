package com.klpc.stadspring.domain.advertVideo.service;

import com.klpc.stadspring.domain.advertVideo.controller.response.AddVideoListResponse;
import com.klpc.stadspring.domain.advertVideo.repository.AdvertVideoRepository;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddVideoListRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.response.AddVideoListResponseCommand;
import com.klpc.stadspring.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertVideoService {

    private final AdvertVideoRepository advertVideoRepository;
    private final S3Util s3Util;

    public AddVideoListResponse addVideoList(AddVideoListRequestCommand requestCommand){
        List<AddVideoListResponseCommand> list = new ArrayList<>();
        for(MultipartFile file : requestCommand.list) {
            String fileName = UUID.randomUUID().toString().replace("-","")+file.getName();
            URL videoUrl = s3Util.uploadVideoToS3(file, "AdvertVideo", fileName);

            list.add(AddVideoListResponseCommand.builder().videoUrl(videoUrl.toString()).build());
        }

        return AddVideoListResponse.builder().data(list).build();
    }
}