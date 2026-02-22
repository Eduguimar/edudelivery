package com.devedu.deliverytracking.domain.service;

import java.time.Duration;

public class DeliveryEstimate {
    private Duration estimatedTime;
    private Double distanceInKm;

    public DeliveryEstimate(Duration estimatedTime, Double distanceInKm) {
        this.estimatedTime = estimatedTime;
        this.distanceInKm = distanceInKm;
    }

    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    public Double getDistanceInKm() {
        return distanceInKm;
    }
}
