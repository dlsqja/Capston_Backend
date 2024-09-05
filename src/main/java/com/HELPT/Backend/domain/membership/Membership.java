package com.HELPT.Backend.domain.membership;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;

    private Long userId;

    private Long gymId;

    private int attendanceDate;

    private LocalDate lastAttendDate;

    private LocalDate startDate;

    private LocalDate endDate;

    @Builder
    public Membership(Long userId, Long gymId) {
        this.userId = userId;
        this.attendanceDate = 1;
        this.gymId = gymId;
        this.startDate = LocalDate.now();
        this.lastAttendDate = LocalDate.now();
    }

    public void addDays(int months)
    {
        this.endDate = startDate.plusMonths(months);
    }

    public void attend(int attendanceDate){
        this.attendanceDate = attendanceDate;
        this.lastAttendDate = LocalDate.now();
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
