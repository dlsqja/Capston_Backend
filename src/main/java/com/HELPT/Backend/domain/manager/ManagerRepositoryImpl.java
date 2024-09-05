package com.HELPT.Backend.domain.manager;

import com.HELPT.Backend.domain.member.Dto.MemberJoinResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static com.HELPT.Backend.domain.member.QMember.member;
import static com.HELPT.Backend.domain.membership.QMembership.membership;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ManagerRepositoryImpl implements ManagerRepositoryCustom{

    @PersistenceContext
    private final EntityManager em;

    private static JPAQueryFactory queryFactory;

    @Autowired
    public void setQueryFactory(EntityManager entityManager)
    {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<MemberJoinResponse> MemberList(Long gymId)
    {

        List<Tuple> results = queryFactory.select(member.userId,member.userName,membership.startDate,membership.endDate)
                .from(member)
                .leftJoin(membership).on(member.userId.eq(membership.userId))
                .where(member.gymId.eq(gymId))
                .fetch();

        return results.stream().map(tuple -> MemberJoinResponse.builder()
                .userId(tuple.get(member.userId))
                .userName(tuple.get(member.userName))
                .startDate(tuple.get(membership.startDate))
                .endDate(tuple.get(membership.endDate))
                .build()).toList();
    }
}
