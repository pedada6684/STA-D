package com.klpc.stadspring.domain.advertVideo.service;

import com.klpc.stadspring.domain.advertVideo.controller.response.*;
import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
import com.klpc.stadspring.domain.advertVideo.repository.AdvertVideoRepository;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddBannerImgRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddVideoListRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.request.ModifyVideoRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.response.AddVideoListResponseCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
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

    /**
     * TV에 광고 영상 출력
     * @param id : 광고 id
     *           increaseSpreadCnt : 광고 노출 수 증가
     * @return 광고 영상 URL
     */
    public GetAdvertVideoResponse getAdvertVideo(Long id){
        AdvertVideo advertVideo = advertVideoRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        advertVideo.increaseSpreadCnt();

        GetAdvertVideoResponse response = GetAdvertVideoResponse.builder().advertVideoUrl(advertVideo.getVideoUrl()).build();
        return response;
    }

    /**
     * 영상 수정
     * @param command
     * @return
     */
    public ModifyVideoResponse modifyVideo(ModifyVideoRequestCommand command){
        System.out.println("******"+command.getVideoId());
        AdvertVideo advertVideo = advertVideoRepository.findById(command.getVideoId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        MultipartFile file = command.getVideo();
        String fileName = UUID.randomUUID().toString().replace("-","")+file.getName();
        URL videoUrl = s3Util.uploadVideoToS3(file,"AdvertVideo",fileName);

        advertVideo.modifyAdvertVideoUrl(videoUrl.toString());

        return ModifyVideoResponse.builder().videoUrl(videoUrl.toString()).build();
    }

    public DeleteResponse deleteResponse(Long id){
        AdvertVideo advertVideo = advertVideoRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        advertVideoRepository.delete(advertVideo);

        return DeleteResponse.builder().result("success").build();
    }

    public AddBannerImgResponse addBannerImg(AddBannerImgRequestCommand command){
        MultipartFile file = command.getImg();
        String fileName = UUID.randomUUID().toString().replaceAll("-","")+file.getName();
        URL imgUrl = s3Util.uploadImageToS3(file,"bannerImg",fileName);

        return AddBannerImgResponse.builder().bannerUrl(imgUrl.toString()).build();
    }
}
