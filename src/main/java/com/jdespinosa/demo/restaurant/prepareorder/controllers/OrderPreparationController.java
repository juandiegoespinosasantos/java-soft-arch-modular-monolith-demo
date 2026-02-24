package com.jdespinosa.demo.restaurant.prepareorder.controllers;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.prepareorder.model.dto.OrderPreparationDTO;
import com.jdespinosa.demo.restaurant.prepareorder.model.enums.OrderPreparationStatuses;
import com.jdespinosa.demo.restaurant.prepareorder.services.IOrderPreparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Order preparation controller implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@RestController
@RequestMapping("/api/v1/preparations")
public class OrderPreparationController implements IOrderPreparationController {

    private final IOrderPreparationService service;

    @Autowired
    public OrderPreparationController(IOrderPreparationService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderPreparationDTO>> findAll() {
        List<OrderPreparationDTO> preps = service.findAll();

        return ResponseEntity.ok(preps);
    }

    @Override
    @GetMapping(path = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderPreparationDTO>> findByStatus(@PathVariable("status") OrderPreparationStatuses status) {
        List<OrderPreparationDTO> preps = service.findByStatus(status);

        return ResponseEntity.ok(preps);
    }

    @Override
    @GetMapping(path = "/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderPreparationDTO> findByOrder(@PathVariable("orderId") Long orderId) {
        Optional<OrderPreparationDTO> opt = service.findByOrderId(orderId);
        if (opt.isEmpty()) throw new NotFoundException("Order", orderId.toString());

        return ResponseEntity.ok(opt.get());
    }

    @Override
    @PostMapping(path = "/order/{orderId}/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderPreparationDTO> start(@PathVariable("orderId") Long orderId) {
        OrderPreparationDTO started = service.startPreparation(orderId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(started);
    }

    @Override
    @PatchMapping(path = "/order/{orderId}/ready", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderPreparationDTO> markReady(@PathVariable("orderId") Long orderId,
                                                         @RequestParam(required = false) String chefNotes) {
        OrderPreparationDTO ready = service.markReady(orderId, chefNotes);

        return ResponseEntity.ok(ready);
    }
}