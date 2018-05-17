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

    @Query("select (sum(coalesce(da.estimatedValue, 0)) + ?1 > ?2) from DirectAcquisition da where da.cpvCode = ?3")
    Optional<Boolean> isOverLimit(BigDecimal amount, BigDecimal limit, String cpvCode);

    @Query("select new com.acquila.common.dto.response.CentralizedDetails(" +
            "da.cpvCode, 'a', count (da), sum (da.estimatedValue), da.type) from DirectAcquisition da " +
            "where da.created > ?1 and da.created < ?2 " +
            "group by da.cpvCode, da.type ")
    Page<CentralizedDetails> getCentralizedData(OffsetDateTime from, OffsetDateTime to, Pageable pageRequest);
}
