package com.HELPT.Backend.domain.notice;

import com.HELPT.Backend.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {

    Optional<List<Notice>> findAllByGymId(Long gymId);
}
