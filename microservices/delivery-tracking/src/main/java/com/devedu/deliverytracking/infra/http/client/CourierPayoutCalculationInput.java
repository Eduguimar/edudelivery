package com.devedu.deliverytracking.infra.http.client;

public class CourierPayoutCalculationInput {
    private Double distanceInKm;

    public CourierPayoutCalculationInput(Double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public Double getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(Double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }
}
