package com.klpc.stadspring.domain.classification.service;

import com.klpc.stadspring.domain.classification.dto.ClassificationRequest;
import com.klpc.stadspring.domain.classification.dto.ClassificationResponse;
import com.klpc.stadspring.domain.classification.dto.UserCategoryRequest;
import com.klpc.stadspring.domain.classification.dto.UserCategoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ClassificationService {
    private final WebClient webClient;

    @Value("${spring.stad-python.url}")
    private String stadPythonUrl;

    public ClassificationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(stadPythonUrl).build(); // FastAPI 서버 URL
    }

    public List<ClassificationResponse> getCategory(List<ClassificationRequest> ClassificationRequestList) {
        return this.webClient.post()
                .uri("/category/info")
                .bodyValue(ClassificationRequestList)
                .retrieve()
                .bodyToFlux(ClassificationResponse.class)
                .collectList()
                .block(); // 비동기를 동기로 변환, 실제 사용시에는 리액티브 스트림 그대로 사용 권장
    }

    public List<UserCategoryResponse> getUserCategory(UserCategoryRequest userCategoryRequest) {
        return this.webClient.post()
                .uri("/category/user")
                .bodyValue(userCategoryRequest)
                .retrieve()
                .bodyToFlux(UserCategoryResponse.class)
                .collectList()
                .block(); // 비동기를 동기로 변환, 실제 사용시에는 리액티브 스트림 그대로 사용 권장
    }

}
