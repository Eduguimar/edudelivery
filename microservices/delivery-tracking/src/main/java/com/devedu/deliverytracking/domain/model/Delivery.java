package com.devedu.deliverytracking.domain.model;

import com.devedu.deliverytracking.domain.exception.DomainException;

import java.math.BigDecimal;
import java.time.Duration;
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

    public UUID addItem(String name, int quantity) {
        Item item = Item.brandNew(name, quantity);
        items.add(item);
        calculateTotalItems();
        return item.getId();
    }

    public void removeItem(UUID itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
        calculateTotalItems();
    }

    public void removeItems() {
        items.clear();
        calculateTotalItems();
    }

    public void editPreparationDetails(PreparationDetails preparationDetails) {
        verifyIfCanBeEdited();

        setSender(preparationDetails.getSender());
        setRecipient(preparationDetails.getRecipient());
        setDistanceFee(preparationDetails.getDistanceFee());
        setCourierPayout(preparationDetails.getCourierPayout());

        setExpectedDeliveryAt(OffsetDateTime.now().plus(preparationDetails.getExpectedDeliveryTime()));
        setTotalCost(this.getDistanceFee().add(this.getCourierPayout()));
    }

    public void place() {
        verifyIfCanBePlaced();
        this.changeStatusTo(DeliveryStatus.WAITING_FOR_COURIER);
        this.setPlacedAt(OffsetDateTime.now());
    }

    public void pickup(UUID courierId) {
        this.setCourierId(courierId);
        this.changeStatusTo(DeliveryStatus.IN_TRANSIT);
        this.setAssignedAt(OffsetDateTime.now());
    }

    public void markAsDelivered() {
        this.changeStatusTo(DeliveryStatus.DELIVERED);
        this.setFulfilledAt(OffsetDateTime.now());
    }

    public void changeItemQuantity(UUID itemId, int quantity) {
        Item item = getItems().stream().filter(i -> i.getId().equals(itemId)).findFirst().orElse(null);
        assert item != null;
        item.setQuantity(quantity);
        calculateTotalItems();
    }

    private void calculateTotalItems() {
        int totalItems = getItems().stream().mapToInt(Item::getQuantity).sum();
        setTotalItems(totalItems);
    }

    private void verifyIfCanBePlaced() {
        if (!isFilled()) {
            throw new DomainException();
        }
        if (!getStatus().equals(DeliveryStatus.DRAFT)) {
            throw  new DomainException();
        }
    }

    private void verifyIfCanBeEdited() {
        if (!getStatus().equals(DeliveryStatus.DRAFT)) {
            throw  new DomainException();
        }
    }

    private boolean isFilled() {
        return this.getSender() != null
                && this.getRecipient() != null
                && this.getTotalCost() != null;
    }

    private void changeStatusTo(DeliveryStatus newStatus) {
        if (newStatus != null && this.getStatus().canNotChangeTo(newStatus)) {
            throw new DomainException(
                    "Invalid status transition from " + this.getStatus() + " to " + newStatus
            );
        }
        this.setStatus(newStatus);
    }

    public static class PreparationDetails {
        private ContactPoint sender;
        private ContactPoint recipient;
        private BigDecimal distanceFee;
        private BigDecimal courierPayout;
        private Duration expectedDeliveryTime;

        public PreparationDetails() {
        }

        public PreparationDetails(ContactPoint sender, ContactPoint recipient, BigDecimal distanceFee,
                                  BigDecimal courierPayout, Duration expectedDeliveryTime) {
            this.sender = sender;
            this.recipient = recipient;
            this.distanceFee = distanceFee;
            this.courierPayout = courierPayout;
            this.expectedDeliveryTime = expectedDeliveryTime;
        }

        public ContactPoint getSender() {
            return sender;
        }

        public PreparationDetails setSender(ContactPoint sender) {
            this.sender = sender;
            return this;
        }

        public ContactPoint getRecipient() {
            return recipient;
        }

        public PreparationDetails setRecipient(ContactPoint recipient) {
            this.recipient = recipient;
            return this;
        }

        public BigDecimal getDistanceFee() {
            return distanceFee;
        }

        public PreparationDetails setDistanceFee(BigDecimal distanceFee) {
            this.distanceFee = distanceFee;
            return this;
        }

        public BigDecimal getCourierPayout() {
            return courierPayout;
        }

        public PreparationDetails setCourierPayout(BigDecimal courierPayout) {
            this.courierPayout = courierPayout;
            return this;
        }

        public Duration getExpectedDeliveryTime() {
            return expectedDeliveryTime;
        }

        public PreparationDetails setExpectedDeliveryTime(Duration expectedDeliveryTime) {
            this.expectedDeliveryTime = expectedDeliveryTime;
            return this;
        }
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
