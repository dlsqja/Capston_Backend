package com.HELPT.Backend.domain.entry_log;

import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntryLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Builder.Default
    private LocalDateTime entryTime = LocalDateTime.now();

}
