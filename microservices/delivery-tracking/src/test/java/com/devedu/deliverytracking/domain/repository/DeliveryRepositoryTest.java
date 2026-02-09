package com.devedu.deliverytracking.domain.repository;

import com.devedu.deliverytracking.domain.model.ContactPoint;
import com.devedu.deliverytracking.domain.model.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

    @Autowired
    DeliveryRepository repository;

    @Test
    public void shouldPersist() {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createValidPreparationDetails());
        delivery.addItem("Computer", 2);
        delivery.addItem("Laptop", 3);

        repository.saveAndFlush(delivery);
        Delivery savedDelivery = repository.findById(delivery.getId()).orElseThrow();

        assertEquals(2, savedDelivery.getItems().size());
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