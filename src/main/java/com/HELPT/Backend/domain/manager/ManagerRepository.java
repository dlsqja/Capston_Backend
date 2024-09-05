package com.HELPT.Backend.domain.manager;

import com.HELPT.Backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long>,ManagerRepositoryCustom{

    Optional<Manager> findByKakaoId(String kakaoId);
}
