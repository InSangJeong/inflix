package kr.co.insang.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsGlobalConfiguration implements WebFluxConfigurer {
    //이 CORS는 인가 서비스의 CORS이고 application.yml 파일은 Gateway의 CORS임!
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                //.allowedOrigins("http://localhost:8080")
                //.allowedOrigins("http://192.168.0.100:8080")
                .allowedOrigins("https://www.insang.co.kr")
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);
    }

}

