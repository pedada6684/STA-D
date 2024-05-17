package com.klpc.stadalert.domain.connect.entity;

import com.klpc.stadalert.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "notifications")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String receiver;

	@Enumerated(EnumType.STRING)
	private NotificationStatus status;

	public static Notification createNewNotifcation(
			String receiver
	){
		Notification notification = new Notification();
		notification.receiver = receiver;
		notification.status = NotificationStatus.UNREAD;
		return notification;
	}

	public void readNotification() {
		this.status = NotificationStatus.READ;
	}

}
