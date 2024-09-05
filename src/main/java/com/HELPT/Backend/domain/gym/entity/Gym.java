package com.HELPT.Backend.domain.gym.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    private String gymName;
    private String chat_link;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gym_registration_id")
    private GymRegistration gymRegistration;

    @Builder.Default
    private Status status = Status.Pending;

    public void updateAddress(Address address) {
        this.address = address;
    }
    public void updateGymName(String gymName) {
        this.gymName = gymName;
    }
    public void updateStatus(Status status) {this.status = status;}
    public void updateGymRegistration(GymRegistration gymRegistration) {this.gymRegistration = gymRegistration;}
    public void updateChatLink(String chat_link) {this.chat_link = chat_link;}
}
