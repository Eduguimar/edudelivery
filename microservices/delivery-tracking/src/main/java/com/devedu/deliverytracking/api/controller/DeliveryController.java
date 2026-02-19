package com.devedu.deliverytracking.api.controller;

import com.devedu.deliverytracking.api.model.DeliveryInput;
import com.devedu.deliverytracking.domain.model.Delivery;
import com.devedu.deliverytracking.domain.service.DeliveryPreparationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/deliveries")
public class DeliveryController {

    private final DeliveryPreparationService deliveryService;

    public DeliveryController(DeliveryPreparationService deliveryService) {
        this.deliveryService = deliveryService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput input) {
        return deliveryService.draft(input);
    }

    @PutMapping("/{deliveryId}")
    public Delivery edit(@PathVariable UUID deliveryId, @RequestBody @Valid DeliveryInput input) {
        return deliveryService.edit(deliveryId, input);
    }
}
