package com.acquila.core.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.acquila.core.entity.Service;

/**
 * Service repository.
 */
public interface ServiceRepository extends JpaRepository<Service, UUID> {

    Page<Service> findAll(Pageable pageRequest);
}
