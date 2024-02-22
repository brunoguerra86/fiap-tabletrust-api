package com.postech.tabletrust.repository;

import com.postech.tabletrust.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findAllByCustomerId(UUID customerId);
    List<Reservation> findAllByRestaurantId(UUID restaurantId);
}
