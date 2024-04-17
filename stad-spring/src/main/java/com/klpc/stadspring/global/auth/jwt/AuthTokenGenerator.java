package com.klpc.stadspring.global.auth.jwt;

import com.klpc.stadspring.global.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthTokenGenerator {
    private static final String BEARER_TYPE= "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME= 1000L * 60 * 10;            // 10분
    private static final long REFRESH_TOKEN_EXPIRE_TIME= 1000L * 60 * 60 * 24 * 90;  // 90일

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public AccessToken generateAT(Long userId){
        long now = new Date().getTime();
        Date accessTokenExpiredDate  = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String subject = userId.toString();
        String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredDate);

        return AccessToken.of(accessToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME);
    }
    public RefreshToken generateRT(Long userId){
        long now = new Date().getTime();
        Date refreshTokenExpiredDate  = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = userId.toString();
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredDate);

        //redis refreshToken 저장
        refreshTokenService.removeRefreshToken(userId);
        refreshTokenService.saveTokenInfo(userId, refreshToken);

        return RefreshToken.of(refreshToken, BEARER_TYPE, REFRESH_TOKEN_EXPIRE_TIME);
    }
}