package com.HELPT.Backend.domain.gymadmission;

import com.HELPT.Backend.domain.gym.entity.Status;
import com.HELPT.Backend.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GymAdmissionResponse {

    private Long memberId;
    private Long gymAdmissionId;
    private String userName;

    public static GymAdmissionResponse toDto(GymAdmission gymAdmission, Member member){
        return GymAdmissionResponse.builder()
                .memberId(member.getUserId())
                .gymAdmissionId(gymAdmission.getId())
                .userName(member.getUserName())
                .build();
    }
}
