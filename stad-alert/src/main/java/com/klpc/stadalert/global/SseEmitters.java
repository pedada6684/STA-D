package com.klpc.stadalert.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SseEmitters {

	private static final long TIMEOUT = 60 * 1000;
	private static final long RECONNECTION_TIMEOUT = 1000L;
	private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

	public SseEmitter subscribe(Long id) {
		SseEmitter emitter = createEmitter();
		emitter.onTimeout(emitter::complete);

		emitter.onError(e -> {
			emitter.complete();
		});

		emitterMap.put(id, emitter);

		emit(id, "SSE connected", "connect");
		return emitter;
	}

	//emit
	public void emit(Long id, Object eventPayload, String eventType) {
		SseEmitter emitter = emitterMap.get(id);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
					.name(eventType)
					.id(String.valueOf("id-1"))
					.data(eventPayload, MediaType.APPLICATION_JSON));
			} catch (IOException e) {
				log.error("failure send media position data, id={}, {}", id, e.getMessage());
			}
		}
	}

	private SseEmitter createEmitter() {
		return new SseEmitter(TIMEOUT);
	}
}