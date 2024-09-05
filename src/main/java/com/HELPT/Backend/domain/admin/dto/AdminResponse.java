package com.HELPT.Backend.domain.admin.dto;

import com.HELPT.Backend.domain.admin.Admin;
import com.HELPT.Backend.domain.manager.Manager;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminResponse {

    private String loginId;
    private String password;

    @Builder
    public AdminResponse(Admin admin) {

    }
}
