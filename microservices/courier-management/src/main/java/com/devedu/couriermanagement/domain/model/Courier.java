package com.devedu.couriermanagement.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.OffsetDateTime;
import java.util.*;

@Entity
public class Courier {

    @Id
    private UUID id;
    private String name;
    private String phone;
    private Integer fulfilledDeliveriesQuantity;
    private Integer pendingDeliveriesQuantity;
    private OffsetDateTime lastFulfilledDeliveryAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "courier")
    private List<AssignedDelivery> pendingDeliveries = new ArrayList<>();

    public Courier() {
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getFulfilledDeliveriesQuantity() {
        return fulfilledDeliveriesQuantity;
    }

    private void setFulfilledDeliveriesQuantity(Integer fulfilledDeliveriesQuantity) {
        this.fulfilledDeliveriesQuantity = fulfilledDeliveriesQuantity;
    }

    public Integer getPendingDeliveriesQuantity() {
        return pendingDeliveriesQuantity;
    }

    private void setPendingDeliveriesQuantity(Integer pendingDeliveriesQuantity) {
        this.pendingDeliveriesQuantity = pendingDeliveriesQuantity;
    }

    public OffsetDateTime getLastFulfilledDeliveryAt() {
        return lastFulfilledDeliveryAt;
    }

    private void setLastFulfilledDeliveryAt(OffsetDateTime lastFulfilledDeliveryAt) {
        this.lastFulfilledDeliveryAt = lastFulfilledDeliveryAt;
    }

    public List<AssignedDelivery> getPendingDeliveries() {
        return Collections.unmodifiableList(this.pendingDeliveries);
    }

    private void setPendingDeliveries(List<AssignedDelivery> pendingDeliveries) {
        this.pendingDeliveries = pendingDeliveries;
    }

    public static Courier brandNew(String name, String phone) {
        Courier courier = new Courier();
        courier.setId(UUID.randomUUID());
        courier.setName(name);
        courier.setPhone(phone);
        courier.setFulfilledDeliveriesQuantity(0);
        courier.setPendingDeliveriesQuantity(0);

        return courier;
    }

    public void assign(UUID deliveryId) {
        this.pendingDeliveries.add(AssignedDelivery.pending(deliveryId, this));
        this.pendingDeliveriesQuantity++;
    }

    public void fulfill(UUID deliveryId) {
        AssignedDelivery delivery = this.pendingDeliveries.stream().filter(
                id -> id.getId().equals(deliveryId)).findFirst().orElseThrow();
        this.pendingDeliveries.remove(delivery);
        this.pendingDeliveriesQuantity--;
        this.fulfilledDeliveriesQuantity++;
        this.lastFulfilledDeliveryAt = OffsetDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Courier that = (Courier) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
