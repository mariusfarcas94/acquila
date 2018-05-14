package com.acquila.core.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.acquila.core.entity.Work;

/**
 * Work repository.
 */
public interface WorkRepository extends JpaRepository<Work, UUID> {

    @Query("select w from Work w where w.year = ?1")
    Page<Work> findAll(int year, Pageable pageRequest);
}
