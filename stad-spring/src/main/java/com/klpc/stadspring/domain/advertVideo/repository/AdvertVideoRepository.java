package com.klpc.stadspring.domain.advertVideo.repository;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertVideoRepository extends JpaRepository<AdvertVideo, Long> {

    @Query("""
           select adv.advert from AdvertVideo adv
           where adv.advert.status = true
           order by adv.clickCnt desc
           limit 3
           """)
    public List<Advert> findAllOrderClick();

    @Query("""
           SELECT a.id FROM AdvertVideo a
           WHERE a.advert.status = true
           """)
    public List<Long> findAllAdvertVideoIds();

    @Query("""
           SELECT adv FROM AdvertVideo adv
           WHERE adv.advert.id = :advertId
                 AND adv.advert.status = true
           """)
    public AdvertVideo findTopByAdvert_Id(Long advertId);

}
