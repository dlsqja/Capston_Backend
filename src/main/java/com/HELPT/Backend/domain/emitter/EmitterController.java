package com.HELPT.Backend.domain.emitter;

import com.HELPT.Backend.global.auth.SecurityUtil;
import com.HELPT.Backend.global.auth.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static com.HELPT.Backend.global.auth.SecurityUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
public class EmitterController {

    private final EmitterService emitterService;

    @Operation(summary = "sse세션연결")
    @GetMapping(value = "/api/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return ResponseEntity.ok(emitterService.subscribe(getCurrentUserId(), lastEventId));
    }
}

