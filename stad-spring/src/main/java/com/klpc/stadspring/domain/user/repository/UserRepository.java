package com.klpc.stadspring.domain.user.repository;

import com.klpc.stadspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
}
