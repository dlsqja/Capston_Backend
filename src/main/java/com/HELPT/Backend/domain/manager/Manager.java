package com.HELPT.Backend.domain.manager;

import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.global.auth.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    private String kakaoId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.MANAGER;

    public void updateGym(Gym gym) {
        this.gym = gym;
    }

    //    private String name;
    //    private String phone;

}
