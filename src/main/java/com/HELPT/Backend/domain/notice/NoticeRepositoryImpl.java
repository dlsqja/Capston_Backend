package com.HELPT.Backend.domain.notice;

import com.HELPT.Backend.domain.membership.MembershipRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;

    private static JPAQueryFactory queryFactory;
}
