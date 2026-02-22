package com.devedu.deliverytracking.domain.service;

import com.devedu.deliverytracking.api.model.ContactPointInput;
import com.devedu.deliverytracking.api.model.DeliveryInput;
import com.devedu.deliverytracking.api.model.ItemInput;
import com.devedu.deliverytracking.domain.exception.DomainException;
import com.devedu.deliverytracking.domain.model.ContactPoint;
import com.devedu.deliverytracking.domain.model.Delivery;
import com.devedu.deliverytracking.domain.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
public class DeliveryPreparationService {

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DeliveryTimeEstimationService deliveryTimeEstimationService;
    @Autowired
    private CourierPayoutCalculationService courierPayoutCalculationService;

    @Transactional
    public Delivery draft(DeliveryInput input) {
        Delivery delivery = Delivery.draft();
        handlePreparation(input, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryInput input) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(DomainException::new);
        delivery.removeItems();
        handlePreparation(input, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }

    private void handlePreparation(DeliveryInput input, Delivery delivery) {
        ContactPointInput inputSender = input.getSender();
        ContactPointInput inputRecipient = input.getRecipient();

        ContactPoint sender = new ContactPoint(inputSender.getZipCode(), inputSender.getStreet(),
                inputSender.getNumber(), inputSender.getComplement(), inputSender.getName(), inputSender.getPhone());

        ContactPoint recipient = new ContactPoint(inputRecipient.getZipCode(), inputRecipient.getStreet(),
                inputRecipient.getNumber(), inputRecipient.getComplement(), inputRecipient.getName(), inputRecipient.getPhone());

        DeliveryEstimate estimate = deliveryTimeEstimationService.estimate(sender, recipient);
        BigDecimal calculatedPayout = courierPayoutCalculationService.calculatePayout(estimate.getDistanceInKm());
        BigDecimal distanceFee = calculateFee(estimate.getDistanceInKm());

        Delivery.PreparationDetails preparationDetails = new Delivery.PreparationDetails(
                sender, recipient, distanceFee, calculatedPayout, estimate.getEstimatedTime()
        );

        delivery.editPreparationDetails(preparationDetails);

        for (ItemInput itemInput : input.getItems()) {
            delivery.addItem(itemInput.getName(), itemInput.getQuantity());
        }
    }

    private BigDecimal calculateFee(Double distanceInKm) {
        return new BigDecimal(3).multiply(new BigDecimal(distanceInKm)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
