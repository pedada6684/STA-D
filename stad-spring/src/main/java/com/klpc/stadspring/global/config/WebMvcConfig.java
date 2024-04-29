package com.klpc.stadspring.global.config;

import com.klpc.stadspring.global.Interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterCeptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authInterCeptor).excludePathPatterns(
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v2/api-docs"
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://mystad.com")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
    }
}
