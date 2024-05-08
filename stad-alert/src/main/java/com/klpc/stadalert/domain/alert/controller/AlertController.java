package com.klpc.stadalert.domain.alert.controller;

import com.klpc.stadalert.domain.connect.controller.request.ConnectRequest;
import com.klpc.stadalert.domain.connect.controller.request.QrLoginRequest;
import com.klpc.stadalert.domain.connect.service.ConnectService;
import com.klpc.stadalert.global.service.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/alert")
public class AlertController {

	final SseEmitters sseEmitters;
	final ConnectService connectService;

	@PostMapping()
	public ResponseEntity<SseEmitter> connect(@RequestBody ConnectRequest request) {
		log.info("CreateConnectRequest: "+ request);
		SseEmitter emitter = sseEmitters.subscribe(request.toCommand());
		return ResponseEntity.ok(emitter);
	}

//	@KafkaListener(topics = "ad-start", groupId = "ad-group", containerFactory = "testEventKafkaListenerContainerFactory")
//	public void onFollowEvent(GetUserInfoResponse event) {
//		log.info("kafka-test: {}", event);
//	}
}