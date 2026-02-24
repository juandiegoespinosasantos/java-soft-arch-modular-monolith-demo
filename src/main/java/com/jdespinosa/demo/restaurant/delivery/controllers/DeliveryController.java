package com.jdespinosa.demo.restaurant.delivery.controllers;

import com.jdespinosa.demo.restaurant.delivery.model.dto.DeliveryDTO;
import com.jdespinosa.demo.restaurant.delivery.model.dto.DeliveryRequestDTO;
import com.jdespinosa.demo.restaurant.delivery.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Delivery controller implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController implements IDeliveryController {

    private final DeliveryService service;

    @Autowired
    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeliveryDTO>> findAll() {
        List<DeliveryDTO> deliveries = service.findAll();

        return ResponseEntity.ok(deliveries);
    }

    @Override
    @GetMapping(path = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeliveryDTO>> findPending() {
        List<DeliveryDTO> deliveries = service.findPending();

        return ResponseEntity.ok(deliveries);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeliveryDTO> deliver(@RequestBody DeliveryRequestDTO requestBody) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.deliver(requestBody));
    }
}