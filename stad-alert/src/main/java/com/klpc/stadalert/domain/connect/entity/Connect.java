package com.klpc.stadalert.domain.connect.entity;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.ToString;

@Entity
@ToString
@Getter
public class Connect extends Notification {

	private String type;

	public static Connect createChatNotification(
		String receiver,
		String type
	) {
		Connect notification = new Connect();
		notification.type = type;

		notification.updateNotificationInfo(
			receiver,
			NotificationStatus.UNREAD
		);
		return notification;
	}
}