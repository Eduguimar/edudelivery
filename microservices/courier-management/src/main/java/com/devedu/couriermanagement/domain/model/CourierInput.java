package com.devedu.couriermanagement.domain.model;

import jakarta.validation.constraints.NotBlank;

public class CourierInput {

    @NotBlank
    private String name;
    @NotBlank
    private String phone;

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
}
