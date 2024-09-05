package com.HELPT.Backend.domain.emitter;

import com.HELPT.Backend.domain.emitter.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notification ID: " + id));
        notification.read();
        notificationRepository.save(notification);
    }
}