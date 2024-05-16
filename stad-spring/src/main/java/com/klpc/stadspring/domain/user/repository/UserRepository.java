package com.klpc.stadspring.domain.user.repository;

import com.klpc.stadspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
    @Query("SELECT u.youtubeInfo.youtubeInfo FROM User u WHERE u.id = :userId")
    String findYoutubeInfoByUserId(Long userId);
}
