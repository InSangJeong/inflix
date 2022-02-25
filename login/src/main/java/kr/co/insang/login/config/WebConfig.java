package kr.co.insang.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
                //.allowedOrigins("*")
                //.allowCredentials(true)
                //.maxAge(3000);
        /*
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:8000")
                .allowedMethods("GET", "POST","PUT", "DELETE", "OPTIONS")
                .allowedHeaders("x-requested-with", "authorization", "content-type", "credential", "X-AUTH-TOKEN", "X-CSRF-TOKEN")//.allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3000);
         */

    }
}
