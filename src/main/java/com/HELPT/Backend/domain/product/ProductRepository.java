package com.HELPT.Backend.domain.product;

import com.HELPT.Backend.domain.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<List<Product>> findAllByGymId(Long gymId);
}
