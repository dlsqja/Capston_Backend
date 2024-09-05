package com.HELPT.Backend.domain.gym.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GymRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long GymRegistrationId;

    private String contactNumber; // 연락처
    private String businessNumber; // 사업자 등록번호
    private String businessType; // 사업자 구분
    private String ownerName; // 사업자 이름
    private String businessFile; // 사업자 등록증 파일 경로
}