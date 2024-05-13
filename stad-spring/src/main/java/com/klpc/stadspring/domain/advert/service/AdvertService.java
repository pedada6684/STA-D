package com.klpc.stadspring.domain.advert.service;

import com.klpc.stadspring.domain.advert.controller.request.ModifyAdvertVideo;
import com.klpc.stadspring.domain.advert.controller.response.*;
import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advert.service.command.request.AddAdvertRequestCommand;
import com.klpc.stadspring.domain.advert.service.command.request.ModifyAdvertRequestCommand;
import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertAdvertVideo;
import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertListByClickResponseCommand;
import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertListByContentResponseCommand;
import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertResponseCommand;
import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
import com.klpc.stadspring.domain.advertVideo.repository.AdvertVideoRepository;
import com.klpc.stadspring.domain.advertVideo.service.AdvertVideoService;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.selectedContent.entity.SelectedContent;
import com.klpc.stadspring.domain.selectedContent.repository.SelectedContentRepository;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvertVideoRepository advertVideoRepository;
    private final AdvertVideoService advertVideoService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SelectedContentRepository selectedContentRepository;
    private final ContentConceptRepository contentConceptRepository;
    private final RestTemplate restTemplate;

    @Value("${spring.stad-stats.url}")
    private String stadStatsUrl;
    /**
     * 광고 추가
     * 순서 : 광고 엔티티(title, description 등) 생성 -> 연관 관계 엔티티 생성 & 광고 엔티티 입력
     * @param command
     * @return
     */
    @Transactional(readOnly = false)
    public AddAdvertResponse addAdvert(AddAdvertRequestCommand command){
        log.info("AddAdvertRequestCommand: "+ command);
        User user = userRepository.findById(command.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.REQUEST_NOT_FOUND));

        Advert adv = Advert.createToAdvert(
                command.getTitle(),
                command.getDescription(),
                command.getStartDate(),
                command.getEndDate(),
                command.getAdvertType(),
                command.getDirectVideoUrl(),
                command.getBannerImgUrl(),
                user,
                command.getAdvertCategory());
        Advert advert = advertRepository.save(adv);

        for(Long i : command.getSelectedContentList()){
            SelectedContent sc = SelectedContent.createToSelectedContent(i);
            SelectedContent selectedContent = selectedContentRepository.save(sc);
            selectedContent.linkAdvert(advert);
        }
        for(String i : command.getAdvertVideoUrlList()){
            Long len = 0L;
            AdvertVideo advertVideo = AdvertVideo.createToAdvertVideo(len,i);
            advertVideo.linkAdvert(advert);
            advertVideoRepository.save(advertVideo);
        }

        AddAdvertResponse addAdvertResponse;
        if(advert.getId()>0)
            addAdvertResponse = AddAdvertResponse.builder().advertId(advert.getId()).build();
        else
            addAdvertResponse = AddAdvertResponse.builder().advertId(advert.getId()).build();

        return addAdvertResponse;
    }

    /**
     * 광고 수정
     * 영상은 각자 별도 수정 진행됨
     * @param command
     * @return
     */
    @Transactional(readOnly = false)
    public ModifyAdvertResponse modifyAdvert(ModifyAdvertRequestCommand command){
        log.info("ModifyAdvertRequestCommand: "+ command);
        Advert advert = advertRepository.findById(command.getAdvertId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        advert.modifyAdvert(
                command.getTitle(),
                command.getDescription(),
                command.getStartDate(),
                command.getEndDate(),
                command.getAdvertCategory(),
                command.getDirectVideoUrl(),
                command.getBannerImgUrl(),
                command.getAdvertType()
        );
        //영상 삭제
        for(AdvertVideo advertVideo : advert.getAdvertVideos()){
            boolean flag = true;
            for(ModifyAdvertVideo av : command.getAdvertVideoUrlList()){
                if(advertVideo.getVideoUrl().equals(av.getAdvertVideoUrl())) {
                    flag = false;
                    break;
                }
            }
            if(flag)
                advertVideoRepository.delete(advertVideo);
        }
        //영상 등록 및 수정
        for(ModifyAdvertVideo av : command.getAdvertVideoUrlList()) {
            //등록
            if (advertVideoRepository.findFirstByVideoUrl(av.getAdvertVideoUrl()).isEmpty()) {
                Long len = 0L;
                AdvertVideo advertVideo = AdvertVideo.createToAdvertVideo(len, av.getAdvertVideoUrl());
                advertVideo.linkAdvert(advert);
                advertVideoRepository.save(advertVideo);
            }
            //수정
            else{
                log.info("######## advertVideoId : "+av.getAdvertVideoId());
                AdvertVideo advertVideo = advertVideoRepository.findById(av.getAdvertVideoId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
                log.info("advertVideo 조회 통과");
                advertVideo.modifyAdvertVideoUrl(av.getAdvertVideoUrl());
            }
        }
        if (!command.getSelectedContentList().isEmpty()) {
            selectedContentRepository.deleteAll(advert.getSelectedContents());
            for (Long i : command.getSelectedContentList()) {
                SelectedContent sc = SelectedContent.createToSelectedContent(i);
                SelectedContent selectedContent = selectedContentRepository.save(sc);
                selectedContent.linkAdvert(advert);
            }
        }

        ModifyAdvertResponse modifyAdvertResponse;
        if(advert.getId()>0)
            modifyAdvertResponse = ModifyAdvertResponse.builder().advertId(advert.getId()).build();
        else
            modifyAdvertResponse = ModifyAdvertResponse.builder().advertId(advert.getId()).build();

        return modifyAdvertResponse;
    }

    /**
     * 광고 삭제
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public DeleteAdvertResponse deleteAdvert(Long id){
        Advert advert = advertRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        if(!advert.getProducts().isEmpty()) {
            Product product = advert.getProducts().get(0);
            product.deleteProduct(product);
            for (ProductType productType : product.getProductType()) {
                productType.deleteProductType(productType);
            }
        }
        advert.deleteAdvert();

        return DeleteAdvertResponse.builder().result("success").build();
    }

    /**
     * 광고 정보 조회
     * @param id : advertId
     * @return
     */
    public GetAdvertResponse getAdvert(Long id){
        Advert advert = advertRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        if(!advert.getStatus()){
            throw new CustomException(ErrorCode.ENTITIY_NOT_FOUND);
        }
        List<Long> selectedContentList = new ArrayList<>();
        for(SelectedContent i : advert.getSelectedContents())
            selectedContentList.add(i.getFixedContentId());

        List<GetAdvertAdvertVideo> advertVideoUrlList = new ArrayList<>();
        for(AdvertVideo i : advert.getAdvertVideos()) {
            GetAdvertAdvertVideo advertVideo = GetAdvertAdvertVideo.builder().advertVideoId(i.getId()).advertVideoUrl(i.getVideoUrl()).build();
            advertVideoUrlList.add(advertVideo);
        }

        GetAdvertResponse response = GetAdvertResponse.builder()
                .title(advert.getTitle())
                .description(advert.getDescription())
                .startDate(advert.getStartDate().toLocalDate().toString())
                .endDate(advert.getEndDate().toLocalDate().toString())
                .type(advert.getAdvertType().toString())
                .directVideoUrl(advert.getDirectVideoUrl())
                .bannerImgUrl(advert.getBannerImgUrl())
                .selectedContentList(selectedContentList)
                .advertVideoUrlList(advertVideoUrlList)
                .category(advert.getAdvertCategory())
                .build();

        return response;
    }

    /**
     * 광고 리스트 조회
     * @param id : userId
     * @return
     */
    public GetAdvertListResponse getAdvertList(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        List<Advert> listByUser = advertRepository.findAllByUser(user);

        List<GetAdvertResponseCommand> responseList = new ArrayList<>();
        for(Advert advert : listByUser){
            List<Long> selectedContentList = new ArrayList<>();
            for(SelectedContent i : advert.getSelectedContents())
                selectedContentList.add(i.getFixedContentId());

            List<GetAdvertAdvertVideo> advertVideoUrlList = new ArrayList<>();
            for(AdvertVideo i : advert.getAdvertVideos()) {
                GetAdvertAdvertVideo getAdvertAdvertVideo = GetAdvertAdvertVideo.builder().advertVideoId(i.getId()).advertVideoUrl(i.getVideoUrl()).build();
                advertVideoUrlList.add(getAdvertAdvertVideo);
            }

            GetAdvertResponseCommand response = GetAdvertResponseCommand.builder()
                    .advertId(advert.getId())
                    .title(advert.getTitle())
                    .description(advert.getDescription())
                    .startDate(advert.getStartDate().toLocalDate().toString())
                    .endDate(advert.getEndDate().toLocalDate().toString())
                    .advertType(advert.getAdvertType().toString())
                    .advertCategory(advert.getAdvertCategory())
                    .directVideoUrl(advert.getDirectVideoUrl())
                    .bannerImgUrl(advert.getBannerImgUrl())
                    .selectedContentList(selectedContentList)
                    .advertVideoUrlList(advertVideoUrlList)
                    .build();

            responseList.add(response);
        }

        return GetAdvertListResponse.builder().data(responseList).build();
    }

    /**
     * 인기 광고 출력
     * ORDER BY click_cnt DESC
     * LIMIT 3
     * @return
     */
    public GetAdvertListByClickResponse getAdvertListByClick(){
        List<Advert> listOrderClick = new ArrayList<>();
        try {
            listOrderClick = advertVideoRepository.findAllOrderClick();
        }catch (CustomException e){
            throw new CustomException(ErrorCode.ORDERBYDESC_ERROR);
        }

        List<GetAdvertListByClickResponseCommand> responseList = new ArrayList<>();
        for(Advert advert : listOrderClick){
            List<Long> selectedContentList = new ArrayList<>();
            for(SelectedContent i : advert.getSelectedContents())
                selectedContentList.add(i.getId());

            List<String> advertVideoUrlList = new ArrayList<>();
            for(AdvertVideo i : advert.getAdvertVideos())
                advertVideoUrlList.add(i.getVideoUrl());

            GetAdvertListByClickResponseCommand response = GetAdvertListByClickResponseCommand.builder()
                    .advertId(advert.getId())
                    .title(advert.getTitle())
                    .description(advert.getDescription())
                    .startDate(advert.getStartDate().toLocalDate().toString())
                    .endDate(advert.getEndDate().toLocalDate().toString())
                    .type(advert.getAdvertType().toString())
                    .directVideoUrl(advert.getDirectVideoUrl())
                    .bannerImgUrl(advert.getBannerImgUrl())
                    .selectedContentList(selectedContentList)
                    .advertVideoUrlList(advertVideoUrlList)
                    .build();

            responseList.add(response);
        }

        return GetAdvertListByClickResponse.builder().data(responseList).build();
    }

    /**
     * 컨텐츠 관련 광고 목록 조회
     * @param contentId
     * @return
     */
    public GetAdvertListByContentResponse getAdvertListByContent(Long contentId){
        log.info("컨텐츠 관련 광고 목록 조회 Service"+'\n'+"contentId : "+contentId);
        ContentConcept contentConcept = contentConceptRepository.findById(contentId).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<Advert> advertList = advertRepository.findAllByContentId(contentId);
        List<GetAdvertListByContentResponseCommand> list = new ArrayList<>();
        List<Long> selectedContentList = new ArrayList<>();
        List<String> advertVideoUrlList = new ArrayList<>();
        for(Advert advert : advertList){
            selectedContentList.clear();
            advertVideoUrlList.clear();

            for(SelectedContent sc : advert.getSelectedContents()){
                selectedContentList.add(sc.getFixedContentId());
            }
            for(AdvertVideo av : advert.getAdvertVideos()){
                advertVideoUrlList.add(av.getVideoUrl());
            }

            GetAdvertListByContentResponseCommand command = GetAdvertListByContentResponseCommand.builder()
                    .advertId(advert.getId())
                    .title(advert.getTitle())
                    .description(advert.getDescription())
                    .startDate(advert.getStartDate().toLocalDate().toString())
                    .endDate(advert.getEndDate().toLocalDate().toString())
                    .advertType(advert.getAdvertType().name())
                    .directVideoUrl(advert.getDirectVideoUrl())
                    .bannerImgUrl(advert.getBannerImgUrl())
                    .selectedContentList(selectedContentList)
                    .advertVideoUrlList(advertVideoUrlList)
                    .advertCategory(advert.getAdvertCategory())
                    .build();

            list.add(command);
        }

        return GetAdvertListByContentResponse.builder().data(list).build();
    }


    public GetAdvertIdListResponse GetAdvertIdList() {
        List<Long> advertIdList = advertRepository.findAllAdvertIds();

        GetAdvertIdListResponse response = GetAdvertIdListResponse.builder()
                .advertIdList(advertIdList)
                .build();

        return response;
    }

    public GetAdvertListResponse GetAdvertById(Long userId) {
        List<Long> advertIdList = GetAdvertIdByUser(userId).getAdvertIdList();

        List<GetAdvertResponseCommand> list = new ArrayList<>();

        for(Long advertId : advertIdList) {
            Advert advert = advertRepository.findById(advertId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

            List<Long> selectedContentList = new ArrayList<>();
            for(SelectedContent i : advert.getSelectedContents())
                selectedContentList.add(i.getId());

            List<GetAdvertAdvertVideo> advertVideoUrlList = new ArrayList<>();
            for(AdvertVideo i : advert.getAdvertVideos()) {
                GetAdvertAdvertVideo getAdvertAdvertVideo = GetAdvertAdvertVideo.builder().advertVideoId(i.getId()).advertVideoUrl(i.getVideoUrl()).build();
                advertVideoUrlList.add(getAdvertAdvertVideo);
            }

            GetAdvertResponseCommand response = GetAdvertResponseCommand.builder()
                    .advertId(advert.getId())
                    .title(advert.getTitle())
                    .description(advert.getDescription())
                    .startDate(advert.getStartDate().toLocalDate().toString())
                    .endDate(advert.getEndDate().toLocalDate().toString())
                    .advertType(advert.getAdvertType().toString())
                    .advertCategory(advert.getAdvertCategory())
                    .directVideoUrl(advert.getDirectVideoUrl())
                    .bannerImgUrl(advert.getBannerImgUrl())
                    .selectedContentList(selectedContentList)
                    .advertVideoUrlList(advertVideoUrlList)
                    .build();

            list.add(response);
        }
        return GetAdvertListResponse.builder().data(list).build();
    }

    public GetAdvertIdListResponse GetAdvertIdByUser(Long userId) {
        String url = stadStatsUrl+"/log/advert-id/list";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", userId);

        GetAdvertIdListResponse response = restTemplate.getForObject(builder.toUriString(), GetAdvertIdListResponse.class);

        log.info("GetAdvertIdListResponse: " + response);

        return response;
    }

}
