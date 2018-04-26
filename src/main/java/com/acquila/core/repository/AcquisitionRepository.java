package com.acquila.core.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.acquila.core.entity.Acquisition;

/**
 * Procedure repository.
 */
public interface AcquisitionRepository extends JpaRepository<Acquisition, UUID> {

    @Query("select (sum(a.estimatedValue) + ?1 > ?2) from Acquisition a where a.cpvCode = ?3")
    boolean isOverLimit(BigDecimal amount, BigDecimal limit, String cpvCode);
}
