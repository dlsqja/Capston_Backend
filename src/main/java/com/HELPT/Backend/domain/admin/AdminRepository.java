package com.HELPT.Backend.domain.admin;

import com.HELPT.Backend.domain.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByLoginId(String loginId);
}
