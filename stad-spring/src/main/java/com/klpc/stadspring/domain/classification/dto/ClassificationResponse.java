package com.klpc.stadspring.domain.classification.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassificationResponse {
    private Long videoId;
    private String category;
}
