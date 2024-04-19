package com.klpc.stadspring.domain.advert.service;

import com.klpc.stadspring.domain.advert.controller.response.AddAdvertResponse;
import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advert.service.command.request.AddAdvertRequestCommand;
import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
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

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvertVideoService advertVideoService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SelectedContentRepository selectedContentRepository;

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
            AdvertVideo advertVideo = AdvertVideo.createToAdvertVideo(0L,i);
            advertVideo.linkAdvert(advert);
        }

        AddAdvertResponse addAdvertResponse;
        if(advert.getId()>0)
            addAdvertResponse = AddAdvertResponse.builder().result("success").build();
        else
            addAdvertResponse = AddAdvertResponse.builder().result("fail").build();

        return addAdvertResponse;
    }

}
