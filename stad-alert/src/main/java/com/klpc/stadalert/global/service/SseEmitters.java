package com.klpc.stadalert.global.service;

import com.klpc.stadalert.domain.connect.entity.Notification;
import com.klpc.stadalert.domain.connect.repository.NotificationRepository;
import com.klpc.stadalert.global.service.command.ConnectCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class SseEmitters {

	private final NotificationRepository notificationRepository;
	private static final long TIMEOUT = 60 * 1000;
	private static final long RECONNECTION_TIMEOUT = 1000L;
	private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();


	public SseEmitter subscribe(ConnectCommand command) {
		log.info("ConnectCommand: " + command);
		SseEmitter emitter = createEmitter();
		emitter.onTimeout(emitter::complete);

		emitter.onError(e -> {
			emitter.complete();
		});
		String emitId = command.getType() + command.getUserId();
		emitterMap.put(emitId, emitter);

		return emit(emitId, "SSE connected", "connect");
	}

	//emit
	public SseEmitter emit(String id, Object eventPayload, String eventType) {
		SseEmitter emitter = emitterMap.get(id);
		Notification notifcation = Notification.createNewNotifcation(id);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
					.name(eventType)
					.data(eventPayload, MediaType.APPLICATION_JSON));
			} catch (IOException e) {
				log.error("failure send media position data, id={}, {}", id, e.getMessage());
			}
			notifcation.readNotification();
		}
		notificationRepository.save(notifcation);
		return emitter;
	}

	private SseEmitter createEmitter() {
		return new SseEmitter(TIMEOUT);
	}
}