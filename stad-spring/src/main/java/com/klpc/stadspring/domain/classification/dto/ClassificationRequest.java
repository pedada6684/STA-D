package com.klpc.stadspring.domain.classification.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassificationRequest {
    private String videoId;
    private String videoDescription;
}
