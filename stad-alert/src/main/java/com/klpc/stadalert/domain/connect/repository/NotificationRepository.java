package com.klpc.stadalert.domain.connect.repository;

import com.klpc.stadalert.domain.connect.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
