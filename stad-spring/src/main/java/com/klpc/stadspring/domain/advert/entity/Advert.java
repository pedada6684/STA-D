package com.klpc.stadspring.domain.advert.entity;

import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.selectedContent.entity.SelectedContent;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 3000)
    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private AdvertType advertType;  //PRODUCT, NOTPRODUCT

    @Column(length = 3000)
    private String directVideoUrl;

    @Column(length = 3000)
    private String bannerImgUrl;

    private String advertCategory;

    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "advert")
    private List<AdvertVideo> advertVideos;

    @OneToMany(mappedBy = "advert")
    private List<SelectedContent> selectedContents;

    @OneToMany(mappedBy = "advert")
    private List<Product> products;

    public static Advert createToAdvert(
            String title,
            String description,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String advertType,
            String directVideoUrl,
            String bannerImgUrl,
            User user,
            String advertCategory
            ){
        Advert advert = new Advert();
        advert.title=title;
        advert.description=description;
        advert.startDate=startDate;
        advert.endDate=endDate;
        if(advertType.equals("PRODUCT"))
            advert.advertType=AdvertType.PRODUCT;
        else
            advert.advertType=AdvertType.NOTPRODUCT;
        if(directVideoUrl!=null && !directVideoUrl.isBlank())
            advert.directVideoUrl=directVideoUrl;
        advert.bannerImgUrl=bannerImgUrl;
        advert.user=user;
        advert.advertCategory=advertCategory;
        advert.status=true;
        return advert;
    }

    public void modifyAdvert(
            String title,
            String description,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String advertCategory,
            String directVideoUrl,
            String bannerImgUrl,
            String advertType
    ){
        this.title=title;
        this.description=description;
        this.startDate=startDate;
        this.endDate=endDate;
        if(advertType.equals("PRODUCT"))
            this.advertType=AdvertType.PRODUCT;
        else
            this.advertType=AdvertType.NOTPRODUCT;
        this.directVideoUrl = directVideoUrl;
        this.bannerImgUrl=bannerImgUrl;
        this.advertCategory=advertCategory;
        this.status=true;
    }

    public void deleteAdvert(){
        this.status=false;
    }
}
