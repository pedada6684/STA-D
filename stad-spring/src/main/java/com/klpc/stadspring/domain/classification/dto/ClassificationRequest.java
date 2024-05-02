package com.klpc.stadspring.domain.classification.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassificationRequest {
    private String videoId;
    private String videoDescription;
}
