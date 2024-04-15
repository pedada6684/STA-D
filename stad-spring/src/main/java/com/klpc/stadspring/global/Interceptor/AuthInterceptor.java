package com.klpc.stadspring.global.Interceptor;

import com.klpc.stadspring.domain.user.service.UserService;
import com.klpc.stadspring.domain.user.service.command.FindUserByIdCommand;
import com.klpc.stadspring.global.auth.jwt.AccessToken;
import com.klpc.stadspring.global.auth.jwt.AuthTokenGenerator;
import com.klpc.stadspring.global.auth.jwt.JwtTokenProvider;
import com.klpc.stadspring.global.auth.jwt.RefreshToken;
import com.klpc.stadspring.global.auth.service.RefreshTokenService;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import com.klpc.stadspring.util.Auth;
import com.klpc.stadspring.util.CookieProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_NAME = "refreshToken";
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenGenerator authTokenGenerator;
    private final CookieProvider cookieProvider;
    private final RefreshTokenService refreshTokenService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }
        if (!checkAnnotation(handler, Auth.class)){//@Auth 어노테이션 없으면
            return true; // 로그인 검증 넘어감
        }

        //JWT 추출
        String accessToken = resolveTokenInRequest(request);
        String refreshToken = getRefreshToken(request);

        try {//AT 유효
            jwtTokenProvider.validateAccessToken(accessToken);
            return allowAccess(request, response, accessToken);
        } catch (ExpiredJwtException e) {//AT만료
            jwtTokenProvider.validateRefreshToken(refreshToken);
            String strMemberId = jwtTokenProvider.extractSubject(refreshToken);
            String redisRefreshToken = refreshTokenService.findTokenByMemberId(Long.parseLong(strMemberId));
            if (redisRefreshToken == null) {
                throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
            }else if (jwtTokenProvider.validateRefreshToken(redisRefreshToken)){
                return allowAccess(request, response, accessToken);
            }
        }
        throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

    /**
     * 검증된 접근일 시 토큰을 재발급하고 접근을 허가하는 메서드
     *
     * @param request
     * @param response
     * @param accessToken
     * @return
     */
    private boolean allowAccess(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        //유저 존재여부 확인
        String strUserId = jwtTokenProvider.extractSubject(accessToken);
        long userId = Long.parseLong(strUserId);
        FindUserByIdCommand command = FindUserByIdCommand.builder()
                .id(Long.parseLong(strUserId))
                .build();
        userService.findUserById(command);

        //AT RT 재발급
        AccessToken newAccessToken = authTokenGenerator.generateAT(userId);
        RefreshToken newRefreshToken = authTokenGenerator.generateRT(userId);
        Cookie cookie = cookieProvider.createCookie(
                "refreshToken",
                newRefreshToken.getRefreshToken(),
                Long.valueOf(newRefreshToken.getExpiresIn()/1000L).intValue()
        );
        response.setHeader("Authorization", "Bearer " + newAccessToken.getAccessToken());
        response.addCookie(cookie);
        request.setAttribute("userId", userId);
        return true;
    }

    /**
     * header에서 토큰 추출하는 메서드
     * @param request HttpServletRequest
     * @return token string
     */
    private String resolveTokenInRequest(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null){
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
        return token;
    }

    /**
     * refreshToken 추출 메서드
     * @param request
     * @return refreshToken
     */
    private String getRefreshToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN_NAME)){
                return cookie.getValue();
            }
        }
        throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
    }


    private boolean checkAnnotation(Object handler, Class<Auth> authClass) {
        //js. html 타입인 view 과련 파일들은 통과한다.(view 관련 요청 = ResourceHttpRequestHandler)
        if (handler instanceof ResourceHttpRequestHandler) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //Auth anntotation이 있는 경우
        if (null != handlerMethod.getMethodAnnotation(authClass) || null != handlerMethod.getBeanType().getAnnotation(authClass)) {
            return true;
        }else {//annotation이 없는 경우
            return false;
        }
    }
}
