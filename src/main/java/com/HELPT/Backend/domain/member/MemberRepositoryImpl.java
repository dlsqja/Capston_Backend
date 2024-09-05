package com.HELPT.Backend.domain.member;

import com.HELPT.Backend.domain.member.Dto.MemberDetailResponse;
import com.HELPT.Backend.domain.member.Dto.MemberJoinResponse;
import com.HELPT.Backend.domain.membership.Membership;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.HELPT.Backend.domain.member.QMember.member;
import static com.HELPT.Backend.domain.membership.QMembership.membership;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    @PersistenceContext
    private final EntityManager em;

    private static JPAQueryFactory queryFactory;

    @Autowired
    public void setQueryFactory(EntityManager entityManager)
    {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Membership attendance(Long userid)
    {
        return queryFactory.selectFrom(membership)
                .where(membership.userId.eq(userid))
                .fetchOne();
    }

    @Override
    public Membership qrVelify(Long userid, Long gymId) {
        return queryFactory.selectFrom(membership)
                .where(membership.userId.eq(userid),membership.gymId.eq(gymId))
                .fetchOne();
    }

    @Override
    public MemberDetailResponse memberDetail(Long memberId) {
        Tuple result = queryFactory
                .select(member.userName, member.gender, member.height, member.weight, member.birthDate, member.profileImage, membership.membershipId, membership.startDate, membership.endDate)
                .from(member)
                .leftJoin(membership).on(member.userId.eq(membership.userId))
                .where(member.userId.eq(memberId))
                .fetchOne();

        return MemberDetailResponse.builder()
                .userName(result.get(member.userName))
                .gender(result.get(member.gender))
                .weight(result.get(member.weight))
                .height(result.get(member.height))
                .membershipId(result.get(membership.membershipId))
                .startDate(result.get(membership.startDate))
                .endDate(result.get(membership.endDate))
                .birthDate(result.get(member.birthDate))
                .profileImage(result.get(member.profileImage))
                .build();
    }

    @Override
    public List<MemberJoinResponse> memberList(Long gymId, String name)
    {
        BooleanExpression namePredicate = member.userName.contains(name);

        List<Tuple> results = queryFactory
                .select(member.userId,member.userName, membership.startDate, membership.endDate)
                .from(member)
                .leftJoin(membership).on(member.userId.eq(membership.userId))
                .where(member.gymId.eq(gymId).and(namePredicate))
                .fetch();

        return results.stream().map(tuple -> MemberJoinResponse.builder()
                .userId(tuple.get(member.userId))
                .userName(tuple.get(member.userName))
                .startDate(tuple.get(membership.startDate))
                .endDate(tuple.get(membership.endDate))
                .build()).toList();
    }
}
