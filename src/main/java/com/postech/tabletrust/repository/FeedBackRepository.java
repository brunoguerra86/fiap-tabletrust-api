package com.postech.tabletrust.repository;

import com.postech.tabletrust.entity.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, UUID> {

    @Query("SELECT f FROM FeedBack f ORDER BY f.restaurantId ASC")
    Page<FeedBack> listFeedback(Pageable pageable);

    List<FeedBack> getFeedBackByRestaurantId(UUID id);
}
