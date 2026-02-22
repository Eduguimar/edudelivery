package com.devedu.couriermanagement.domain.service;

import com.devedu.couriermanagement.domain.model.Courier;
import com.devedu.couriermanagement.domain.model.CourierInput;
import com.devedu.couriermanagement.domain.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CourierRegistrationService {

    @Autowired
    private CourierRepository courierRepository;

    public Courier create(CourierInput input) {
        Courier courier = Courier.brandNew(input.getName(), input.getPhone());

        return courierRepository.saveAndFlush(courier);
    }

    public Courier update(UUID courierId, CourierInput input) {
        Courier courier = courierRepository.findById(courierId).orElseThrow();
        courier.setName(input.getName());
        courier.setPhone(input.getPhone());

        return courierRepository.saveAndFlush(courier);
    }
}
