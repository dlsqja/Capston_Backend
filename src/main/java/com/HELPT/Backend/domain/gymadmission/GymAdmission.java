package com.HELPT.Backend.domain.gymadmission;

import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.entity.Status;
import com.HELPT.Backend.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GymAdmission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Builder.Default
    private Status status = Status.Pending;

    public void updateGym(Gym gym){this.gym = gym;}
    public void updateMember(Member member){this.member = member;}
}
