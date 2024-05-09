package com.klpc.stadstats.domain.tmp.controller;

import com.klpc.stadstats.domain.tmp.controller.request.TmpRequest;
import com.klpc.stadstats.domain.tmp.controller.response.TmpResponse;
import com.klpc.stadstats.domain.tmp.entity.GetUserInfoResponse;
import com.klpc.stadstats.domain.tmp.entity.Tmp;
import com.klpc.stadstats.domain.tmp.service.TmpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/stats")
public class TmpController {

	final TmpService tmpService;

	@PostMapping("/tmp")
	public ResponseEntity<TmpResponse> qrLogin(@RequestBody TmpRequest request) {
		log.info("TmpRequest: "+ request);
		Tmp tmp = tmpService.tmpMethod(request.toCommand());
		TmpResponse response = TmpResponse.from(tmp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@KafkaListener(topics = "kafka-test", groupId = "test-group", containerFactory = "testEventKafkaListenerContainerFactory")
	public void onFollowEvent(GetUserInfoResponse event) {
		log.info("kafka-test: {}", event);
	}
}