package com.acquila.core.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.acquila.core.entity.Procedure;

/**
 * Procedure repository.
 */
public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {

    @Query("select p from Procedure p where p.year = ?1")
    Page<Procedure> findAll(int year, Pageable pageRequest);
}
