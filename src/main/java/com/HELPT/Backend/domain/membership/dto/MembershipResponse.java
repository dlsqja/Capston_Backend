package com.HELPT.Backend.domain.membership.dto;

import com.HELPT.Backend.domain.membership.Membership;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembershipResponse {

    private Long gymId;
    private Long membershipId;
    private int attendanceDate;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public MembershipResponse(Membership membership) {
        this.gymId = membership.getGymId();
        this.membershipId = membership.getMembershipId();
        this.attendanceDate = membership.getAttendanceDate();
        this.startDate = membership.getStartDate();
        this.endDate = membership.getEndDate();
    }
}
