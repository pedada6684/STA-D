package com.klpc.stadspring.domain.log.repository;

import com.klpc.stadspring.domain.log.entity.AdvertClickLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdvertClickLogRepository extends JpaRepository<AdvertClickLog, Long> {
    @Query("SELECT COUNT(a) FROM AdvertClickLog a WHERE a.advertId = :advertId")
    Optional<Long> countAddClickLogByAdvertId(@Param("advertId") Long advertId);
}
