package com.HELPT.Backend.domain.emitter;

import com.HELPT.Backend.domain.emitter.entity.Notification;
import com.HELPT.Backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiver(Member member);
}