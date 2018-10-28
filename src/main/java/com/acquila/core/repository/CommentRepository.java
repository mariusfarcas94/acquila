package com.acquila.core.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acquila.core.entity.Comment;

/**
 * CommentDetails repository.
 */
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findAllByAcquisition_Id(UUID acquisitionId);
}
