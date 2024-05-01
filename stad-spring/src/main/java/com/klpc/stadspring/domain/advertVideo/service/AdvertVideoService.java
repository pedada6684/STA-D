package com.klpc.stadspring.domain.advertVideo.service;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advertVideo.controller.response.*;
import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
import com.klpc.stadspring.domain.advertVideo.repository.AdvertVideoRepository;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddBannerImgRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddVideoListRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.request.ModifyVideoRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.response.AddVideoListResponseCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.response.GetAdvertVideoListByUserResponseCommand;
import com.klpc.stadspring.domain.log.repository.AdvertStatisticsRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import com.klpc.stadspring.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertVideoService {

    private final AdvertVideoRepository advertVideoRepository;
    private final AdvertRepository advertRepository;
    private final AdvertStatisticsRepository advertStatisticsRepository;
    private final S3Util s3Util;

    /**
     * 광고 영상 리스트 추가
     * @param requestCommand
     * @return
     */
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public ModifyVideoResponse modifyVideo(ModifyVideoRequestCommand command){
        System.out.println("******"+command.getVideoId());
        AdvertVideo advertVideo = advertVideoRepository.findById(command.getVideoId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        MultipartFile file = command.getVideo();
        String fileName = UUID.randomUUID().toString().replace("-","")+file.getName();
        URL videoUrl = s3Util.uploadVideoToS3(file,"AdvertVideo",fileName);

        advertVideo.modifyAdvertVideoUrl(videoUrl.toString());

        return ModifyVideoResponse.builder().videoUrl(videoUrl.toString()).build();
    }

    /**
     * 영상 삭제
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public DeleteResponse deleteVideo(Long id){
        AdvertVideo advertVideo = advertVideoRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        advertVideoRepository.delete(advertVideo);

        return DeleteResponse.builder().result("success").build();
    }

    /**
     * 배너 이미지 추가
     * @param command
     * @return
     */
    @Transactional(readOnly = false)
    public AddBannerImgResponse addBannerImg(AddBannerImgRequestCommand command){
        MultipartFile file = command.getImg();
        String fileName = UUID.randomUUID().toString().replaceAll("-","")+file.getName();
        URL imgUrl = s3Util.uploadImageToS3(file,"bannerImg",fileName);

        return AddBannerImgResponse.builder().bannerUrl(imgUrl.toString()).build();
    }

    public GetAdvertVideoListByUserResponse getAdvertVideoByUser(Long userId){
        // ==지운 - 관심사 추려주는 알고리즘 만들면 바꾸기==
        List<String> userCategory = new ArrayList<>();
        userCategory.add("개발");
        userCategory.add("푸드");
        userCategory.add("튀김");
        // ===========================================

        List<Long> listByUser = new ArrayList<>();

        for (int i = 0; i < userCategory.size(); i++) {
            // 인기 광고
            List<Long> listByCategory = advertRepository.findAdvertIdByCategory(userCategory.get(i));

            // 해당 카테고리에 속하는 advert 중 광고 클릭수와 판매량의 합이 높은 순으로 정렬하여 3:2:1 비율로 유저 맞춤 광고에 삽입
            LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
            Map<Long, Long> logAndAdvertIdMap = new HashMap<>();
            for (Long advertId : listByCategory) {
                Object[] results = advertStatisticsRepository.getTotalLog(advertId, thirtyDaysAgo)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

                Object[] result = (Object[]) results[0];
                Long totalAdvertClick = result[0] != null ? (Long) result[0] : 0L;
                Long totalOrder = result[2] != null ? (Long) result[2] : 0L;

                Long sumAdvertClickAndOrder = totalAdvertClick + totalOrder;
                logAndAdvertIdMap.put(sumAdvertClickAndOrder, advertId);
            }
            List<Long> keySetList = new ArrayList<>(logAndAdvertIdMap.keySet());

            // 내림차순 정렬
            keySetList.sort(Comparator.reverseOrder());

            for (int j = 0; j < 3 - i; j++) {
                if (keySetList.size() > j) {
                    listByUser.add(logAndAdvertIdMap.get(keySetList.get(j)));
                }
            }

            // 랜덤 광고
            List<Long> randomListByCategory = advertRepository.findRandomAdvertIdByCategory(userCategory.get(i));
            int cnt = 0;
            for (Long videoId : randomListByCategory) {
                for (int k = 0; k < listByUser.size() && cnt < 3 - i; k++) {
                    if (Objects.equals(videoId, listByCategory.get(k))) {
                        cnt++;
                        listByCategory.add(videoId);
                    }
                }
            }
        }

        List<GetAdvertVideoListByUserResponseCommand> responseList = new ArrayList<>();
        for (Long id: listByUser) {
            // ============= 민형 - 영상 길이 나오면 고치기 ===============
            AdvertVideo video = advertVideoRepository.findTopByAdvert_Id(id);
            if (video != null) {
                GetAdvertVideoListByUserResponseCommand command = GetAdvertVideoListByUserResponseCommand.builder()
                        .videoId(video.getId())
                        .videoUrl(video.getVideoUrl())
                        .build();

                responseList.add(command);
            }

        }
        return GetAdvertVideoListByUserResponse.builder()
                .data(responseList)
                .build();
    }
}
