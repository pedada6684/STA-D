package com.klpc.stadstats.domain.log.repository;

import com.klpc.stadstats.domain.log.entity.AdvertVideoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public interface AdvertVideoLogRepository extends JpaRepository<AdvertVideoLog, Long> {
    @Query("SELECT COUNT(a) FROM AdvertVideoLog a WHERE a.advertId = :advertId AND a.regDate >= :oneHourAgo")
    Optional<Long> countAdvertVideoLog(@Param("advertId") Long advertId, @Param("oneHourAgo") LocalDateTime oneHourAgo);
}
