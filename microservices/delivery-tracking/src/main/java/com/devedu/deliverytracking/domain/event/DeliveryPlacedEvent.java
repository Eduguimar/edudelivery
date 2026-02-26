package com.devedu.deliverytracking.domain.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public class DeliveryPlacedEvent {

    private final OffsetDateTime ocurredAt;
    private final UUID deliveryId;

    public DeliveryPlacedEvent(OffsetDateTime ocurredAt, UUID deliveryId) {
        this.ocurredAt = ocurredAt;
        this.deliveryId = deliveryId;
    }

    public OffsetDateTime getOcurredAt() {
        return ocurredAt;
    }

    public UUID getDeliveryId() {
        return deliveryId;
    }

    @Override
    public String toString() {
        return "DeliveryPlacedEvent{" +
                "ocurredAt=" + ocurredAt +
                ", deliveryId=" + deliveryId +
                '}';
    }
}
