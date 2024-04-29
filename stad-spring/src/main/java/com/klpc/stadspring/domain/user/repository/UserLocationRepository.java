package com.klpc.stadspring.domain.user.repository;

import com.klpc.stadspring.domain.user.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    long deleteByIdAndUser_Id(Long locationId, Long userId);
}
