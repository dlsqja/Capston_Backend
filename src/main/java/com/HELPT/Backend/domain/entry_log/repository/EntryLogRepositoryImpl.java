package com.HELPT.Backend.domain.entry_log.repository;
import com.HELPT.Backend.domain.entry_log.EntryLog;
import com.HELPT.Backend.domain.entry_log.EntryLogResponse;
import com.HELPT.Backend.domain.member.Dto.MemberJoinResponse;
import com.HELPT.Backend.domain.member.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


import static com.HELPT.Backend.domain.entry_log.QEntryLog.entryLog;
import static com.HELPT.Backend.domain.gym.entity.QGym.gym;
import static com.HELPT.Backend.domain.member.QMember.member;
import static com.HELPT.Backend.domain.membership.QMembership.membership;

@Repository
@RequiredArgsConstructor
public class EntryLogRepositoryImpl implements EntryLogRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<EntryLogResponse> findByNameAndGymIdAndEntryDate(String name, Long gymId, LocalDate date){
        List<Tuple> results = queryFactory
                .select(member.userName, entryLog.entryTime)
                .from(entryLog)
                .join(entryLog.member, member)
                .join(entryLog.gym, gym)
                .where(member.userName.contains(name)
                        .and(gym.id.eq(gymId))
                        .and(entryLog.entryTime.year().eq(date.getYear()))
                        .and(entryLog.entryTime.month().eq(date.getMonthValue()))
                        .and(entryLog.entryTime.dayOfMonth().eq(date.getDayOfMonth())))
                .fetch();
        return results.stream().map(result -> EntryLogResponse.builder()
                .userName(result.get(member.userName))
                .entryTime(result.get(entryLog.entryTime))
                .build()).toList();
    }
}
