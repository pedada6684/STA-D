package com.klpc.stadalert.domain.connect.controller;

import com.klpc.stadalert.domain.connect.controller.request.ConnectRequest;
import com.klpc.stadalert.global.service.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ConnectController {

	final SseEmitters sseEmitters;

	@PostMapping("/connect")
	public ResponseEntity<SseEmitter> connect(@RequestBody ConnectRequest request) {
		log.info("CreateConnectRequest: "+ request);
		SseEmitter emitter = sseEmitters.subscribe(request.toCommand());
		return ResponseEntity.ok(emitter);
	}
}
