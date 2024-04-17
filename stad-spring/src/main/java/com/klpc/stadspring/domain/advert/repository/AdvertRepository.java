package com.klpc.stadspring.domain.advert.repository;

import com.klpc.stadspring.domain.advert.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advert,Long> {
}
