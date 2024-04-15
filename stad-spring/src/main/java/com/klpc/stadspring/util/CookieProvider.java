package com.klpc.stadspring.util;

import com.klpc.stadspring.global.auth.jwt.RefreshToken;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {
    public Cookie createCookie(String name, String value, int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public Cookie createRefreshTokenCookie(RefreshToken refreshToken){
         return createCookie(
             "refreshToken",
             refreshToken.getRefreshToken(),
             Long.valueOf(refreshToken.getExpiresIn()/1000L).intValue()
         );
    }
}