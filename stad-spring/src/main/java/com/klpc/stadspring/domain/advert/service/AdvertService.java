package com.klpc.stadspring.domain.advert.service;

import com.klpc.stadspring.domain.advert.controller.response.AddAdvertResponse;
import com.klpc.stadspring.domain.advert.controller.response.DeleteAdvertResponse;
import com.klpc.stadspring.domain.advert.controller.response.ModifyAdvertResponse;
import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advert.service.command.request.AddAdvertRequestCommand;
import com.klpc.stadspring.domain.advert.service.command.request.ModifyAdvertRequestCommand;
import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
import com.klpc.stadspring.domain.advertVideo.repository.AdvertVideoRepository;
import com.klpc.stadspring.domain.advertVideo.service.AdvertVideoService;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.selectedContent.entity.SelectedContent;
import com.klpc.stadspring.domain.selectedContent.repository.SelectedContentRepository;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.bytedeco.javacv.FFmpegFrameGrabber;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvertVideoRepository advertVideoRepository;
    private final AdvertVideoService advertVideoService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SelectedContentRepository selectedContentRepository;

    /**
     * 광고 추가
     * 순서 : 광고 엔티티(title, description 등) 생성 -> 연관 관계 엔티티 생성 & 광고 엔티티 입력
     * @param command
     * @return
     */
    public AddAdvertResponse addAdvert(AddAdvertRequestCommand command){
        User user = userRepository.findById(command.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.REQUEST_NOT_FOUND));

        Advert adv = Advert.createToAdvert(
                command.getTitle(),
                command.getDescription(),
                command.getStartDate(),
                command.getEndDate(),
                command.getCategory(),
                command.getDescription(),
                command.getBannerImgUrl(),
                user);
        Advert advert = advertRepository.save(adv);

        for(Long i : command.getSelectedContentList()){
            SelectedContent sc = SelectedContent.createToSelectedContent(i);
            SelectedContent selectedContent = selectedContentRepository.save(sc);
            selectedContent.linkAdvert(advert);
        }
        for(String i : command.getAdvertVideoUrlList()){
            Long len = 0L;
            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(i)) {
                grabber.start();
                len = (Long) (grabber.getLengthInTime() / 1000); // Convert to milliseconds
            } catch (Exception e) {
                throw new CustomException(ErrorCode.AWSS3_ERROR);
            }
            AdvertVideo advertVideo = AdvertVideo.createToAdvertVideo(len,i);
            advertVideo.linkAdvert(advert);
        }

        AddAdvertResponse addAdvertResponse;
        if(advert.getId()>0)
            addAdvertResponse = AddAdvertResponse.builder().result("success").build();
        else
            addAdvertResponse = AddAdvertResponse.builder().result("fail").build();

        return addAdvertResponse;
    }

    /**
     * 광고 수정
     * 영상은 각자 별도 수정 진행됨
     * @param command
     * @return
     */
    public ModifyAdvertResponse modifyAdvert(ModifyAdvertRequestCommand command){
        Advert advert = advertRepository.findById(command.getAdvertId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        advert.modifyAdvert(
                command.getTitle(),
                command.getDescription(),
                command.getStartDate(),
                command.getEndDate(),
                command.getCategory(),
                command.getDirectVideoUrl(),
                command.getBannerImgUrl()
        );
        if(!command.getSelectedContentList().isEmpty()) {
            selectedContentRepository.deleteAll(advert.getSelectedContents());
            for (Long i : command.getSelectedContentList()) {
                SelectedContent sc = SelectedContent.createToSelectedContent(i);
                SelectedContent selectedContent = selectedContentRepository.save(sc);
                selectedContent.linkAdvert(advert);
            }
        }

        ModifyAdvertResponse modifyAdvertResponse;
        if(advert.getId()>0)
            modifyAdvertResponse = ModifyAdvertResponse.builder().result("success").build();
        else
            modifyAdvertResponse = ModifyAdvertResponse.builder().result("fail").build();

        return modifyAdvertResponse;
    }

    public DeleteAdvertResponse deleteAdvert(Long id){
        Advert advert = advertRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        advertRepository.delete(advert);
        return DeleteAdvertResponse.builder().result("success").build();
    }

}
