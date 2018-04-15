package com.acquila.core.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.acquila.core.entity.Procedure;

/**
 * Procedure repository.
 */
public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {

    Page<Procedure> findAll(Pageable pageRequest);
}
