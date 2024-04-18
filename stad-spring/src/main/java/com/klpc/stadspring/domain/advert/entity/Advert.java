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

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private AdvertCategory advertCategory;  //PRODUCT, NOTPRODUCT

    @Column(length = 3000)
    private String fullVideoUrl;

    @Column(length = 3000)
    private String directVideoUrl;

    @Column(length = 3000)
    private String directImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "advert")
    private List<AdvertVideo> advertVideos;

    @OneToMany(mappedBy = "advert")
    private List<SelectedContent> selectedContents;

    @OneToMany(mappedBy = "advert")
    private List<Product> products;
}
