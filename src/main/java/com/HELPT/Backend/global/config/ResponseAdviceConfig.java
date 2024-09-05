package com.HELPT.Backend.global.config;

import com.HELPT.Backend.global.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestControllerAdvice
@Slf4j
public class ResponseAdviceConfig implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getContainingClass().getName().contains("springdoc")&&
        !MediaType.TEXT_EVENT_STREAM_VALUE.equals(returnType.getGenericParameterType().getTypeName());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        int status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
        if (status == 200) {
            return ApiResponse.successResponse(body);
        }
        return body;
    }
}
