package com.HELPT.Backend.global.error;

import com.HELPT.Backend.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.HELPT.Backend.global.error.ErrorCode.NOT_EXIST_DATA;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException ex, HttpServletRequest request) {
        if (isSseRequest(request)) {
            log.error("[CustomException] SSE request errCode : " + ex.getErrorCode());
            log.error("[CustomException] SSE request errMsg : " + ex.getMessage());
            return ResponseEntity.status(ex.getErrorCode().getHttpStatus().value()).build();  // SSE 요청일 경우 빈 ResponseEntity 반환
        }
        log.error("[CustomException] errCode : " + ex.getErrorCode());
        log.error("[CustomException] errMsg : " + ex.getMessage());
        if (ex.getErrorCode() == NOT_EXIST_DATA){
            return ResponseEntity.status(ex.getErrorCode().getHttpStatus().value()).body(null);
        }
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus().value()).body(ApiResponse.failResponse(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex,HttpServletRequest request) {
        if (isSseRequest(request)) {
            log.error("[RuntimeException] SSE request errMsg : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // SSE 요청일 경우 빈 ResponseEntity 반환
        }
        log.error("[RuntimeException] errMsg : " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.errorResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex,HttpServletRequest request) {
        if (isSseRequest(request)) {
            log.error("[Exception] SSE request errMsg : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // SSE 요청일 경우 빈 ResponseEntity 반환
        }
        log.error("[Exception] errMsg : " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.errorResponse(ex.getMessage()));
    }
    private boolean isSseRequest(HttpServletRequest request) {
        return "text/event-stream".equalsIgnoreCase(request.getHeader("Accept"));
    }
}
