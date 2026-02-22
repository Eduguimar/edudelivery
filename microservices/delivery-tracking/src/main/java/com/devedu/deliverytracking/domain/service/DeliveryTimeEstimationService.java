package com.devedu.deliverytracking.domain.service;

import com.devedu.deliverytracking.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {
    DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver);
}
