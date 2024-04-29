package com.klpc.stadspring.domain.log.repository;

import com.klpc.stadspring.domain.log.entity.AdvertVideoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdvertVideoLogRepository extends JpaRepository<AdvertVideoLog, Long> {
    @Query("SELECT COUNT(a) FROM AdvertVideoLog a WHERE a.advertId = :advertId")
    Optional<Long> countAdvertVideoLog(@Param("advertId") Long advertId);
}
