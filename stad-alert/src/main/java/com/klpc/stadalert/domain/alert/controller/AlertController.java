package com.klpc.stadalert.domain.alert.controller;

import com.klpc.stadalert.domain.alert.controller.event.AdvertsStartEvent;
import com.klpc.stadalert.domain.alert.controller.event.ContentStartEvent;
import com.klpc.stadalert.domain.alert.controller.event.ContentStopEvent;
import com.klpc.stadalert.global.response.ErrorCode;
import com.klpc.stadalert.global.response.exception.CustomException;
import com.klpc.stadalert.global.service.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/alert")
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
		if (emitter == null){
			throw new CustomException(ErrorCode.EMIT_NOT_FOUND);
		}
	}

	@KafkaListener(topics = "content-stop", groupId = "contents-group", containerFactory = "ContentStopEventKafkaListenerContainerFactory")
	public void onContentStopEvent(ContentStopEvent event) {
		log.info("ContentStopEvent: " + event);
		SseEmitter emitter = sseEmitters.emit(
				"app" + event.getUserId(),
				event,
				"Content Stop"
		);
		if (emitter == null){
			throw new CustomException(ErrorCode.EMIT_NOT_FOUND);
		}
	}

	@KafkaListener(topics = "adverts-start", groupId = "adverts-group", containerFactory = "AdvertsStartEventKafkaListenerContainerFactory")
	public void onAdvertsStartEvent(AdvertsStartEvent event) {
		log.info("AdvertsStartEvent: " + event);
		SseEmitter emitter = sseEmitters.emit(
				"app" + event.getUserId(),
				event,
				"Adverts Start"
		);
		if (emitter == null){
			throw new CustomException(ErrorCode.EMIT_NOT_FOUND);
		}
	}
}