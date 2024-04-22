package com.klpc.stadspring.domain.example.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProfileResponse {
    String profileImgUrl;
}
