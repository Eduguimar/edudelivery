package com.devedu.couriermanagement.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class AssignedDelivery {

    @Id
    private UUID id;
    private OffsetDateTime assignedAt;

    @ManyToOne(optional = false)
    private Courier courier;

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

    private Courier getCourier() {
        return courier;
    }

    private void setCourier(Courier courier) {
        this.courier = courier;
    }

    static AssignedDelivery pending(UUID deliveryId, Courier courier) {
        AssignedDelivery delivery = new AssignedDelivery();
        delivery.setId(deliveryId);
        delivery.setAssignedAt(OffsetDateTime.now());
        delivery.setCourier(courier);

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
