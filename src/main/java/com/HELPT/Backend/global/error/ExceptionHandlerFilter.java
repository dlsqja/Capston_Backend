package com.HELPT.Backend.global.error;

import com.HELPT.Backend.global.common.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException ex) {
            setErrorResponse(ex.getErrorCode().getHttpStatus(), response, ex);
        } catch (Exception ex) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {
        logger.error("[ExceptionHandlerFilter] errMsg : " + ex.getMessage());

        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        ApiResponse apiResponse = ApiResponse.errorResponse(ex.getMessage());
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);

        if ("text/event-stream".equals(response.getContentType())) {
            response.getOutputStream().write(jsonResponse.getBytes());
        } else {
            response.getWriter().write(jsonResponse);
        }
    }
}
