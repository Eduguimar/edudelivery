package com.devedu.deliverytracking.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryStatusTest {

    @Test
    void draft_canChangeToWaitingForCourier() {
        assertTrue(
                DeliveryStatus.DRAFT.canChangeTo(DeliveryStatus.WAITING_FOR_COURIER)
        );
    }

    @Test
    void draft_canNotChangeToInTransit() {
        assertTrue(
                DeliveryStatus.DRAFT.canNotChangeTo(DeliveryStatus.IN_TRANSIT)
        );
    }

    @Test
    void waitingForCourier_canChangeToInTransit() {
        assertTrue(
                DeliveryStatus.WAITING_FOR_COURIER.canChangeTo(DeliveryStatus.IN_TRANSIT)
        );
    }

}