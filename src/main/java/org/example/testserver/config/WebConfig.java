package org.example.testserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowCredentials(true)
                .allowedHeaders("x-requested-with", "authorization", "content-type", "credential", "X-AUTH-TOKEN", "X-CSRF-TOKEN")
                .allowedMethods("POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS");
    }
}