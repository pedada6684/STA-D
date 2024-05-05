package com.klpc.stadspring.domain.advertVideo.service;

import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advertVideo.controller.response.*;
import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
import com.klpc.stadspring.domain.advertVideo.repository.AdvertVideoRepository;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddBannerImgRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddVideoListRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.request.ModifyVideoRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.response.AddVideoListResponseCommand;
import com.klpc.stadspring.domain.log.repository.AdvertStatisticsRepository;
import com.klpc.stadspring.domain.selectedContent.repository.SelectedContentRepository;
import com.klpc.stadspring.global.RedisService;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import com.klpc.stadspring.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertVideoService {

    private final AdvertVideoRepository advertVideoRepository;
    private final AdvertRepository advertRepository;
    private final AdvertStatisticsRepository advertStatisticsRepository;
    private final SelectedContentRepository selectedContentRepository;
    private final RedisService redisService;
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

    /**
     * 로그인 시 유저 맞춤 광고 큐 조회 서비스
     * @param userId
     * @return
     */
    public List<String> getAdvertVideoByUser(Long userId){
        log.info("유저 맞춤 광고 큐 조회 서비스" + "\n" + "userId : " + userId);

        // TODO: 지운 - 관심사 추려주는 알고리즘 만들면 바꾸기
        List<String> userCategory = new ArrayList<>();
        userCategory.add("개발");
        userCategory.add("푸드");
        userCategory.add("튀김");
        // ===========================================

        List<Long> advertIdListByUser = new ArrayList<>();

        for (int i = 0; i < userCategory.size(); i++) {
            // 인기 광고
            List<Long> listByCategory = advertRepository.findAdvertIdByCategory(userCategory.get(i));
            if (listByCategory == null) {
                new CustomException(ErrorCode.ENTITIY_NOT_FOUND);
                continue;
            }

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
                    advertIdListByUser.add(logAndAdvertIdMap.get(keySetList.get(j)));
                }
            }

            // 랜덤 광고
            List<Long> randomListByCategory = advertRepository.findRandomAdvertIdByCategory(userCategory.get(i));
            if (randomListByCategory == null) {
                new CustomException(ErrorCode.ENTITIY_NOT_FOUND);
                continue;
            }
            int cnt = 0;
            for (Long videoId : randomListByCategory) {
                for (int k = 0; k < advertIdListByUser.size() && cnt < 3 - i; k++) {
                    if (Objects.equals(videoId, listByCategory.get(k))) {
                        cnt++;
                        listByCategory.add(videoId);
                    }
                }
            }
        }

        List<String> videoUrlListByUser = new ArrayList<>();
        // 광고 id로 광고 video id 조회
        for (Long id: advertIdListByUser) {
            AdvertVideo video = advertVideoRepository.findTopByAdvert_Id(id);
            if (video != null) {
                videoUrlListByUser.add(video.getVideoUrl());
            }
        }

        redisService.createUserAdQueue(userId, videoUrlListByUser);

        return videoUrlListByUser;
    }

    /**
     * 콘텐츠 시청할 때 만들 최종 광고 리스트 조회
     * @param userId
     * @param conceptId
     * @return
     */
    public GetFinalAdvertVideoListResponse getFinalAdvertVideoList(Long userId, Long conceptId) {
        log.info("콘텐츠 시청할 때 만들 최종 광고 리스트 조회 서비스" +"\n" + "userId : " + userId + "\n" + "contentId : " + conceptId);

        List<String> finalList = new ArrayList<>();

        List<String> videoUrlListByUser = redisService.popUserAdQueue(userId);
        for (String tmp : videoUrlListByUser) {
            finalList.add(tmp);
        }

        // 유저 맞춤 광고 큐에서 2개 추출하지 못한 경우 랜덤으로 추출
        while (finalList.size() < 2) {
            AdvertVideo video = advertVideoRepository.findRandomTop();
            finalList.add(video.getVideoUrl());
        }

        // 콘텐츠 고정 광고 1개 추출
        Long advertId = selectedContentRepository.findRandomTopByConceptId(conceptId);
        AdvertVideo video = advertVideoRepository.findTopByAdvert_Id(advertId);
        if (video != null) {
            finalList.add(video.getVideoUrl());
        }

        // 랜덤 기업 광고 1개 추출
        advertId = advertRepository.findRandomNotProductAdvertId();
        video = advertVideoRepository.findTopByAdvert_Id(advertId);
        if (video != null) {
            finalList.add(video.getVideoUrl());
        }

        // 최종 큐에 광고가 4개가 들어가야 함
        while (finalList.size() < 4) {
            video = advertVideoRepository.findRandomTop();
            finalList.add(video.getVideoUrl());
        }

        return GetFinalAdvertVideoListResponse.builder()
                .data(finalList)
                .build();
    }

    /**
     * 영상 스트리밍
     * @param httpHeaders
     * @param pathStr
     * @return
     */
    public ResponseEntity<ResourceRegion> streamingAdvertVideo(HttpHeaders httpHeaders, String pathStr) {
        // 파일 존재 확인
        try {
            UrlResource video = new UrlResource(pathStr);
            // - 파일 시스템 경로에 접근하기 위해서는 "file:"로 시작
            // - HTTP 프로토콜을 통해 자원에 접근하기 위해서는 "https:"로 시작
            // - FTP를 통해서 접근하기 위해서는 "ftp:"로 시작
            ResourceRegion resourceRegion;
            long chunkSize = 1024 * 1024;
            long contentLength = video.contentLength();

            Optional<HttpRange> optional = httpHeaders.getRange().stream().findFirst();
            HttpRange httpRange;
            if (optional.isPresent()) {
                log.info("ContentsService.streaming");
                httpRange = optional.get();
                long start = httpRange.getRangeStart(contentLength);
                long end = httpRange.getRangeEnd(contentLength);
                long rangeLength = Long.min(chunkSize, end - start + 1);
                resourceRegion = new ResourceRegion(video, start, rangeLength);
            } else {
                long rangeLength = Long.min(chunkSize, contentLength);
                //  chunk 사이즈로 자른 시작값부터 청크 사이즈 만큼 ResoureRegion을 return
                resourceRegion = new ResourceRegion(video, 0, rangeLength);
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES)) // 스트리밍을 위해 다운로드 받는 영상 캐시의 지속시간은 10분
                    .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    // header에 담긴 range 범위만큼 return
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes") // 데이터 타입은 bytes라고 헤더에 입력
                    .body(resourceRegion); // body에 가공한 데이터를 담아서 리턴
            // 영상이 재생됨에 따라 해당 url로 다시 request를 요청하여 영상정보를 받아와서 재생
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
