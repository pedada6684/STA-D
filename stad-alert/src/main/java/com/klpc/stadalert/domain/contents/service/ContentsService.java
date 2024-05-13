package com.klpc.stadalert.domain.contents.service;

import com.klpc.stadalert.domain.contents.dto.FindContentDetailDto;
import com.klpc.stadalert.domain.contents.entity.ContentDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentsService {

	@Value("${spring.stad-spring.url}")
	private String stadSpringUrl;

	public ContentDetail findContentsDetail(Long contentDetailId) {
		FindContentDetailDto contentDetail = new RestTemplate().getForObject(
				stadSpringUrl+"/contents-detail/"+contentDetailId,
				FindContentDetailDto.class
		);
		log.info("findContentsDetail: "+ contentDetail);
		return ContentDetail.from(contentDetail);
	}
}
