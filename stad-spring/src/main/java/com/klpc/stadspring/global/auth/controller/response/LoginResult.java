package com.klpc.stadspring.global.auth.controller.response;

import com.klpc.stadspring.global.auth.jwt.AccessToken;
import com.klpc.stadspring.global.auth.jwt.RefreshToken;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResult {
    AccessToken accessToken;
    RefreshToken refreshToken;
}