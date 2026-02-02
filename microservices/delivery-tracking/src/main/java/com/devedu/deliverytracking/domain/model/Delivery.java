package com.devedu.deliverytracking.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

public class Delivery {
    private UUID id;
    private UUID courierId;

    private DeliveryStatus status;

    private OffsetDateTime placedAt;
    private OffsetDateTime assignedAt;
    private OffsetDateTime expectedDeliveryAt;
    private OffsetDateTime fulfilledAt;

    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;

    private Integer totalItems;
    private List<Item> items = new ArrayList<>();

    private ContactPoint sender;
    private ContactPoint recipient;

    Delivery() {}

    public static Delivery draft() {
        Delivery delivery = new Delivery();
        delivery.setId(UUID.randomUUID());
        delivery.setStatus(DeliveryStatus.DRAFT);
        delivery.setTotalItems(0);
        delivery.setTotalCost(BigDecimal.ZERO);
        delivery.setCourierPayout(BigDecimal.ZERO);
        delivery.setDistanceFee(BigDecimal.ZERO);

        return delivery;
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public UUID getCourierId() {
        return courierId;
    }

    private void setCourierId(UUID courierId) {
        this.courierId = courierId;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    private void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public OffsetDateTime getPlacedAt() {
        return placedAt;
    }

    private void setPlacedAt(OffsetDateTime placedAt) {
        this.placedAt = placedAt;
    }

    public OffsetDateTime getAssignedAt() {
        return assignedAt;
    }

    private void setAssignedAt(OffsetDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public OffsetDateTime getExpectedDeliveryAt() {
        return expectedDeliveryAt;
    }

    private void setExpectedDeliveryAt(OffsetDateTime expectedDeliveryAt) {
        this.expectedDeliveryAt = expectedDeliveryAt;
    }

    public OffsetDateTime getFulfilledAt() {
        return fulfilledAt;
    }

    private void setFulfilledAt(OffsetDateTime fulfilledAt) {
        this.fulfilledAt = fulfilledAt;
    }

    public BigDecimal getDistanceFee() {
        return distanceFee;
    }

    private void setDistanceFee(BigDecimal distanceFee) {
        this.distanceFee = distanceFee;
    }

    public BigDecimal getCourierPayout() {
        return courierPayout;
    }

    private void setCourierPayout(BigDecimal courierPayout) {
        this.courierPayout = courierPayout;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    private void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    private void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    private void setItems(List<Item> items) {
        this.items = items;
    }

    public ContactPoint getSender() {
        return sender;
    }

    private void setSender(ContactPoint sender) {
        this.sender = sender;
    }

    public ContactPoint getRecipient() {
        return recipient;
    }

    private void setRecipient(ContactPoint recipient) {
        this.recipient = recipient;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(id, delivery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
