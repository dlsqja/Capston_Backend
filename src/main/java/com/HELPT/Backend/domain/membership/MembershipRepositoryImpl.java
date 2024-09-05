package com.HELPT.Backend.domain.membership;

import com.HELPT.Backend.domain.member.MemberRepositoryCustom;
import com.HELPT.Backend.domain.product.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static com.HELPT.Backend.domain.product.QProduct.product;

@Repository
@RequiredArgsConstructor
public class MembershipRepositoryImpl implements MembershipRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;

    private static JPAQueryFactory queryFactory;

    @Autowired
    public void setQueryFactory(EntityManager entityManager)
    {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    public Product findProduct(Long productId)
    {
        return queryFactory.selectFrom(product)
                .where(product.productId.eq(productId)).fetchOne();
    }
}
