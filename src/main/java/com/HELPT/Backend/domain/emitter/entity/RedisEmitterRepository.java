//package com.HELPT.Backend.domain.emitter;
//
//import com.HELPT.Backend.domain.emitter.entity.Notification;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import javax.annotation.PostConstruct;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Repository
//public class RedisEmitterRepository implements EmitterRepository {
//
//    private static final String EMITTER_KEY_PREFIX = "emitter:";
//    private static final String EVENT_KEY_PREFIX = "event:";
//
//    @Autowired
//    private final RedisTemplate<String, Object> redisTemplate;
//    private HashOperations<String, String, SseEmitter> hashOpsEmitters;
//    private HashOperations<String, String, Notification> hashOpsEvents;
//
//    public RedisEmitterRepository(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @PostConstruct
//    private void init() {
//        hashOpsEmitters = redisTemplate.opsForHash();
//        hashOpsEvents = redisTemplate.opsForHash();
//    }
//
//    @Override
//    public SseEmitter save(String emitterId, SseEmitter emitter) {
//        hashOpsEmitters.put(EMITTER_KEY_PREFIX, emitterId, emitter);
//        return emitter;
//    }
//
//    @Override
//    public void deleteById(String emitterId) {
//        hashOpsEmitters.delete(EMITTER_KEY_PREFIX, emitterId);
//    }
//
//    @Override
//    public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId) {
//        return hashOpsEmitters.entries(EMITTER_KEY_PREFIX + memberId);
//    }
//
//    @Override
//    public void saveEventCache(String eventId, Notification notification) {
//        hashOpsEvents.put(EVENT_KEY_PREFIX, eventId, notification);
//    }
//
//    @Override
//    public Map<String, Notification> findAllEventCacheStartWithByMemberId(String memberId) {
//        return hashOpsEvents.entries(EVENT_KEY_PREFIX + memberId);
//    }
//}
