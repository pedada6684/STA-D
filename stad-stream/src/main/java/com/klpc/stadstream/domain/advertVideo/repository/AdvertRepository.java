package com.klpc.stadstream.domain.advertVideo.repository;


import com.klpc.stadstream.domain.advertVideo.entity.Advert;
import com.klpc.stadstream.domain.advertVideo.entity.AdvertVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    public Optional<Advert> findFirstByAdvertVideos_Id(Long advertVideoId);
}
