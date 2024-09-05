package com.HELPT.Backend.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNAUTHORIZED("인증되지않은 요청입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS_TOKEN("유효하지않은 액세스 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("유효하지않은 리프레시 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_ACCESS_TOKEN("액세스 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_REFRESH_TOKEN("리프레시 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    BAD_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_USER("존재하지 않는 유저입니다.", HttpStatus.UNAUTHORIZED),
    EXIST_MEMBER("이미 등록된 회원입니다.", HttpStatus.UNAUTHORIZED),
    NOT_EXIST_MEMBER("등록되지 않는 회원입니다.", HttpStatus.UNAUTHORIZED),
    EXIST_REQUEST("존재하는 요청입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_DATA("데이터가 존재하지 않습니다.",HttpStatus.OK),
    PAY_CANCEL("결제 취소",HttpStatus.BAD_REQUEST),
    PAY_FAILED("결제 실패",HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
