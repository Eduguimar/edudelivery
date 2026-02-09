package com.devedu.couriermanagement.domain.repository;

import com.devedu.couriermanagement.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {
}
