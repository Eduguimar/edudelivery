package com.devedu.couriermanagement.domain.model;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class AssignedDelivery {

    private UUID id;
    private OffsetDateTime assignedAt;

    public AssignedDelivery() {
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public OffsetDateTime getAssignedAt() {
        return assignedAt;
    }

    private void setAssignedAt(OffsetDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    static AssignedDelivery pending(UUID deliveryId) {
        AssignedDelivery delivery = new AssignedDelivery();
        delivery.setId(deliveryId);
        delivery.setAssignedAt(OffsetDateTime.now());

        return delivery;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AssignedDelivery that = (AssignedDelivery) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
