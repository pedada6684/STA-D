package com.klpc.stadspring.domain.user.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProfileResponse {
    String profileImgUrl;
}
