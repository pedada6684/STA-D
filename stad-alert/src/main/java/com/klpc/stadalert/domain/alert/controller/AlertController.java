package com.klpc.stadalert.domain.alert.controller;

import com.klpc.stadalert.domain.alert.controller.event.AdvertsStartEvent;
import com.klpc.stadalert.domain.alert.controller.event.ContentPlayEvent;
import com.klpc.stadalert.domain.alert.controller.event.ContentStartEvent;
import com.klpc.stadalert.domain.alert.controller.event.ContentStopEvent;
import com.klpc.stadalert.global.service.SseEmitters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AlertController {

	final SseEmitters sseEmitters;


	@KafkaListener(topics = "content-start", groupId = "contents-group", containerFactory = "ContentStartEventKafkaListenerContainerFactory")
	public void onContentStartEvent(ContentStartEvent event) {
		log.info("ContentStartEvent: " + event);
		SseEmitter emitter = sseEmitters.emit(
				"app" + event.getUserId(),
				event,
				"Content Start"
		);
	}

	@KafkaListener(topics = "content-stop", groupId = "contents-group", containerFactory = "ContentStopEventKafkaListenerContainerFactory")
	public void onContentStopEvent(ContentStopEvent event) {
		log.info("ContentStopEvent: " + event);
		SseEmitter emitter = sseEmitters.emit(
				"app" + event.getUserId(),
				event,
				"Content Stop"
		);
	}

	@KafkaListener(topics = "adverts-start", groupId = "adverts-group", containerFactory = "AdvertsStartEventKafkaListenerContainerFactory")
	public void onAdvertsStartEvent(AdvertsStartEvent event) {
		log.info("AdvertsStartEvent: " + event);
		SseEmitter emitter = sseEmitters.emit(
				"app" + event.getUserId(),
				event,
				"Adverts Start"
		);
	}

	@GetMapping("/play-content/{userId}/{detailId}")
	@Operation(summary = "앱에서 영상 플레이 요청", description = "앱에서 영상 플레이 요청")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "정상 작동 완료")
	})
	ResponseEntity<?> playContent(@PathVariable Long detailId, @PathVariable Long userId) {
		SseEmitter emit = sseEmitters.emit(
				"tv" + userId,
				new ContentPlayEvent(userId, detailId),
				"Content Play Request"
		);
		return ResponseEntity.ok().build();
	}
}