package com.klpc.stadspring.domain.advertVideo.repository;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertVideoRepository extends JpaRepository<AdvertVideo, Long> {

    @Query("""
           select adv.advert from AdvertVideo adv
           order by adv.clickCnt desc
           limit 3
           """)
    public List<Advert> findAllOrderClick();

    @Query("SELECT a.id FROM AdvertVideo a")
    public List<Long> findAllAdvertVideoIds();

    @Query("SELECT a.id FROM AdvertVideo a WHERE a.advert.id = :advertId")
    public Long findIdByAdvertId(Long advertId);

}
