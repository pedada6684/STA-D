package com.klpc.stadstats.domain.tmp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Entity
@ToString
@Getter
public class Tmp {

	@Id
	private Long id;
	private String type;

	public static Tmp createChatNotification(
		String type
	) {
		Tmp notification = new Tmp();
		notification.type = type;
		return notification;
	}
}