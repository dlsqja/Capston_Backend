package com.HELPT.Backend.domain.admin;

import com.HELPT.Backend.global.auth.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    private String loginId;
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ADMIN;
}
