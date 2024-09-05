package com.HELPT.Backend.domain.emitter;

import com.HELPT.Backend.domain.emitter.entity.Notification;
import com.HELPT.Backend.domain.emitter.entity.NotificationType;
import com.HELPT.Backend.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmitterService {

    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final NotificationRepository notificationRepository;

    private static final Long DEFAULT_TIMEOUT = 5L * 1000 * 60;

    public SseEmitter subscribe(Long memberId, String lastEventId) {
//        log.info(String.valueOf(memberId));
//        log.info(lastEventId);
        String emitterId = memberId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(memberId));
        if (!sseEmitters.isEmpty()) {
            sseEmitters.forEach((key, sseEmitter) -> {
                log.info("Resetting sseEmitter {} -> {}", key, emitterId);
                emitterRepository.deleteById(key);
                sseEmitter.complete();
            });
        }
        SseEmitter sseEmitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        sseEmitter.onCompletion(() -> {
            emitterRepository.deleteById(emitterId);
        });
        sseEmitter.onTimeout(() -> {
            emitterRepository.deleteById(emitterId);
        });
        sseEmitter.onError((e) -> {
            emitterRepository.deleteById(emitterId);
        });
        sseEmitter.onError((e) -> emitterRepository.deleteById(emitterId));

        sendToClient(sseEmitter, emitterId, emitterId, "EventStream Created. [memberId=" + memberId + "]");

        /* client가 미수신한 event 목록이 존재하는 경우 */
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
//        Map<String, Object> evnetList = emitterRepository.findAllEventCache();
//        evnetList.entrySet().stream()
//                .forEach(entry -> log.info(entry.getKey()));
        if (lastEventId == null || lastEventId.isEmpty()) {
            // If lastEventId is not provided, send all cached events
            eventCaches.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> sendToClient(sseEmitter, entry.getKey(), emitterId, entry.getValue()));
        } else {
            // Send only events that were missed based on lastEventId
            eventCaches.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(sseEmitter, entry.getKey(), emitterId, entry.getValue()));
        }

        return sseEmitter;
    }

    private int compareEventIds(String lastEventId, String currentEventId) {
        String[] lastParts = lastEventId.split("_");
        String[] currentParts = currentEventId.split("_");

        Long lastTimestamp = Long.parseLong(lastParts[1]);
        Long currentTimestamp = Long.parseLong(currentParts[1]);

        return lastTimestamp.compareTo(currentTimestamp);
    }

    public void send(Member receiver, String content, NotificationType type, String url) {
        Notification notification = notificationRepository.save(createNotification(receiver, content, type, url));
        String receiverId = String.valueOf(receiver.getUserId());
        /* 로그인한 client의 sseEmitter 전체 호출 */
        String eventId = receiverId + "_" + System.currentTimeMillis();
//        log.info("send : {}", eventId);
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);
        if (sseEmitters.isEmpty()) {
//            log.info("save cashe : {}", eventId);
            emitterRepository.saveEventCache(eventId, notification);
//            log.info("save cashe complete: {}", eventId);
            return;
        }
        sseEmitters.forEach(
                (key, sseEmitter) -> {
//                    log.info("key, notification : {}, {}", key, notification);
                    emitterRepository.saveEventCache(key, notification); //저장
                    sendToClient(sseEmitter, eventId,key, notification); //전송
                }
        );
    }

    /**
     * [SSE 통신]notification type별 data 생성
     */
    private Notification createNotification(Member receiver, String content, NotificationType type, String url) {
        if(type.equals(NotificationType.NOTICE)) { //공지
            return Notification.builder()
                    .receiver(receiver)
                    .content(content)
                    .notificationType(type)
                    .url(url)
                    .build();
        } else {
            return null;
        }
    }
    /**
     * [SSE 통신]dummy data 생성
     * : 503 Service Unavailable 방지
     */
    private Notification createDummyNotification(Member receiver) {
        return Notification.builder()
                .receiver(receiver)
                .content("send dummy data to client.")
                .notificationType(NotificationType.NOTICE)
                .url("/")
                .build();
    }

    /**
     * [SSE 통신]notification type별 event 전송
     */
    private void send(SseEmitter sseEmitter, String eventId, String emitterId, Object data) {
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data, MediaType.APPLICATION_JSON));
//            log.info("Notification sent to emitter: {} {}", data , sseEmitter);
        } catch (IOException exception) {
            if (exception.getMessage().contains("Broken pipe")) {
                log.warn("Client disconnected: emitterId={}, error={}", emitterId, exception.getMessage());
            } else {
                log.error("Error sending notification: emitterId={}, error={}", emitterId, exception.getMessage());
            }
            emitterRepository.deleteById(emitterId);
            sseEmitter.completeWithError(exception);
        }
    }

    /**
     * [SSE 통신]
     */
    private void sendToClient(SseEmitter sseEmitter, String eventId, String emitterId, Object data) {
        try {
            send(sseEmitter, eventId, emitterId, data);
//            log.info("Notification sent to emitter: {}, eventId: {}, data: {}", emitterId, eventId, data);
        } catch (Exception e) {
            log.error("Error sending to client: emitterId={}, error={}", emitterId, e.getMessage());
            emitterRepository.deleteById(emitterId);
            throw new RuntimeException("Connection Failed.");
        }
    }
}
