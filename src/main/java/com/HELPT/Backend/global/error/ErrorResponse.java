package com.HELPT.Backend.global.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private int status;
    private String message;

    public String convertToJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}
