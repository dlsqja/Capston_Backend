//package com.HELPT.Backend.global.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsMvcConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
////        corsRegistry.addMapping("/**").allowedMethods("*");
//        corsRegistry.addMapping("/api/**")
//                .allowedOriginPatterns("http://localhost:*") // 와일드카드를 사용한 출처 패턴 설정
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*");
//    }
//}