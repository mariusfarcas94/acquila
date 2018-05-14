package com.acquila.core.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.acquila.core.entity.Service;

/**
 * Service repository.
 */
public interface ServiceRepository extends JpaRepository<Service, UUID> {

    @Query("select s from Service s where s.year = ?1")
    Page<Service> findAll(int year, Pageable pageRequest);
}
