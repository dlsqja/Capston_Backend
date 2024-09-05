package com.HELPT.Backend.domain.membership;

import com.HELPT.Backend.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,Long>,MembershipRepositoryCustom {

    Optional<Membership> findByUserIdAndGymId(Long userId, Long gymId);
    Optional<Membership> findByUserId(Long userId);
}
