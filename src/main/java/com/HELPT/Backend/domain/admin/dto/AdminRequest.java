package com.HELPT.Backend.domain.admin.dto;

import com.HELPT.Backend.domain.admin.Admin;
import com.HELPT.Backend.domain.manager.Manager;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminRequest {

    private String loginId;
    private String password;

    public Admin toEntity(){
        return Admin.builder()
                .loginId(loginId)
                .password(password)
                .build();
    }
}
