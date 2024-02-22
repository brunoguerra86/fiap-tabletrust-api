package com.postech.tabletrust.repository;

import com.postech.tabletrust.entities.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comments, UUID> {
    @Query("SELECT c FROM Comments c ORDER BY c.restaurantId ASC")
    Page<Comments> listComments(Pageable pageable);
}
