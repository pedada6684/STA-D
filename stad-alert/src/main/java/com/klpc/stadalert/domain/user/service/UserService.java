package com.klpc.stadalert.domain.user.service;

import com.klpc.stadalert.domain.user.entity.User;
import com.klpc.stadalert.domain.user.service.dto.FindUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	@Value("${spring.user-service.url}")
	private String userServiceUrl;

	public User findUser(Long userId) {
		FindUserDto findUser = new RestTemplate().getForObject(
			userServiceUrl + "/user?userId="+userId,
			FindUserDto.class
		);
		log.debug("findUser: " + findUser);
		return User.createNewUser(findUser);
	}
}
