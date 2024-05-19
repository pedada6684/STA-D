package com.klpc.stadstream.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final Environment environment;

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
            .name(jwt)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
        );

        List<Server> servers = new ArrayList<>();
        String[] activeProfiles = environment.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            if (activeProfile.equals("prod")){
                Server prod_server = new Server();
                prod_server.setUrl("https://www.mystad.com/steam");
                prod_server.description("pord server");
                servers.add(prod_server);
            }
            else if (activeProfile.equals("dev")){
                Server local_server = new Server();
                local_server.setUrl("http://localhost:8083/stream");
                local_server.description("local server");
                servers.add(local_server);
            }
        }

        return new OpenAPI()
            .components(new Components())
            .info(apiInfo())
            .servers(servers)
            .addSecurityItem(securityRequirement)
            .components(components);
    }

    private Info apiInfo() {
        return new Info()
            .title("STAD") // API의 제목
            .description("TV - APP 연동 광고 추천서비스 stad의 문서입니다.") // API에 대한 설명
            .version("1.0.0"); // API의 버전
    }
}