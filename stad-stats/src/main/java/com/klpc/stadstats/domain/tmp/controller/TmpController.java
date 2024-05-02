package com.klpc.stadstats.domain.tmp.controller;

import com.klpc.stadstats.domain.tmp.controller.request.TmpRequest;
import com.klpc.stadstats.domain.tmp.controller.response.TmpResponse;
import com.klpc.stadstats.domain.tmp.entity.Tmp;
import com.klpc.stadstats.domain.tmp.service.TmpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TmpController {

	final TmpService tmpService;

	@PostMapping("tmp")
	public ResponseEntity<TmpResponse> qrLogin(@RequestBody TmpRequest request) {
		log.info("TmpRequest: "+ request);
		Tmp tmp = tmpService.tmpMethod(request.toCommand());
		TmpResponse response = TmpResponse.from(tmp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}