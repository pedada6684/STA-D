package com.klpc.stadspring.domain.classification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassificationResponse {
    private String videoId;
    private String category;
}
