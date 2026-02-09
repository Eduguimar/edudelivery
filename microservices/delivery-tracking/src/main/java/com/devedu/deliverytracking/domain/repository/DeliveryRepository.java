package com.devedu.deliverytracking.domain.repository;

import com.devedu.deliverytracking.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
