package com.devedu.deliverytracking.domain.model;

import com.devedu.deliverytracking.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeToPlaced() {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createValidPreparationDetails());
        delivery.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotChangeToPlacedWithNoPreparationDetails() {
        Delivery delivery = Delivery.draft();

        assertThrows(DomainException.class, delivery::place);

        assertEquals(DeliveryStatus.DRAFT, delivery.getStatus());
        assertNull(delivery.getPlacedAt());
    }

    private Delivery.PreparationDetails createValidPreparationDetails() {
        ContactPoint sender = new ContactPoint("12345-678", "Rua Marques do Sapucaí", "25",
                "AP2", "Marcola Test", "(11)989897678");

        ContactPoint recipient = new ContactPoint("98765-432", "Rua Bambinos", "30",
                "No", "Baltazar Mathias", "(11)989897998");

        return new Delivery.PreparationDetails().setSender(sender)
                .setRecipient(recipient).setDistanceFee(new BigDecimal("15.00"))
                .setCourierPayout(new BigDecimal("12.00")).setExpectedDeliveryTime(Duration.ofHours(10));
    }
}