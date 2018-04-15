package com.acquila.core.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acquila.core.entity.OrderNumber;

/**
 * Procedure repository.
 */
public interface OrderNumberRepository extends JpaRepository<OrderNumber, UUID> {

    Optional<OrderNumber> findByYear(int year);
}
