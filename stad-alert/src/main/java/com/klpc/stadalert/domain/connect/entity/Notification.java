package com.klpc.stadalert.domain.connect.entity;

import com.klpc.stadalert.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "notifications")
@Getter
@ToString
public abstract class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String receiver;

	@Enumerated(EnumType.STRING)
	private NotificationStatus status;

	void updateNotificationInfo(
		String receiver,
		NotificationStatus status
	) {
		this.receiver = receiver;
		this.status = status;
	}

	public void readNotification() {
		this.status = NotificationStatus.READ;
	}

}
