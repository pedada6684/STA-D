package com.klpc.stadspring.domain.classification.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCategoryRequest {
    Long userId;
    String text;
}
