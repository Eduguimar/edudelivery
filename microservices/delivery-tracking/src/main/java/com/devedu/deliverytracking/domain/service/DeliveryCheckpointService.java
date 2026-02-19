package com.devedu.deliverytracking.domain.service;

import com.devedu.deliverytracking.domain.exception.DomainException;
import com.devedu.deliverytracking.domain.model.Delivery;
import com.devedu.deliverytracking.domain.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DeliveryCheckpointService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public void place(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(DomainException::new);
        delivery.place();
        deliveryRepository.saveAndFlush(delivery);
    }

    public void pickup(UUID deliveryId, UUID courierId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(DomainException::new);
        delivery.pickup(courierId);
        deliveryRepository.saveAndFlush(delivery);
    }

    public void complete(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(DomainException::new);
        delivery.markAsDelivered();
        deliveryRepository.saveAndFlush(delivery);
    }
}
