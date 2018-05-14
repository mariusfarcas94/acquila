package com.acquila.core.repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.acquila.common.dto.response.CentralizedDetails;
import com.acquila.core.entity.Acquisition;

/**
 * Procedure repository.
 */
public interface AcquisitionRepository extends JpaRepository<Acquisition, UUID> {

    @Query("select (sum(coalesce(a.estimatedValue, 0)) + ?1 > ?2) from Acquisition a where a.cpvCode = ?3")
    Optional<Boolean> isOverLimit(BigDecimal amount, BigDecimal limit, String cpvCode);

    @Query("select new com.acquila.common.dto.response.CentralizedDetails(" +
            "a.cpvCode, 'a', count (a), sum (a.estimatedValue), a.type) from Acquisition a " +
            "where a.created > ?1 and a.created < ?2 " +
            "group by a.cpvCode, a.type ")
    Page<CentralizedDetails> getCentralizedData(OffsetDateTime from, OffsetDateTime to, Pageable pageRequest);
}
