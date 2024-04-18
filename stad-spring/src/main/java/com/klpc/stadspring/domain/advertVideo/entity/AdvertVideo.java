package com.klpc.stadspring.domain.advertVideo.entity;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.selectedContent.entity.SelectedContent;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdvertVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long len;

    @Column(length = 3000)
    private String videoUrl;

    private Long spreadCnt;

    private Long clickCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advert_id")
    private Advert advert;

}
