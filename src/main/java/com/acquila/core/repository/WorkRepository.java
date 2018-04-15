package com.acquila.core.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.acquila.core.entity.Work;

/**
 * Work repository.
 */
public interface WorkRepository extends JpaRepository<Work, UUID> {

    Page<Work> findAll(Pageable pageRequest);
}
