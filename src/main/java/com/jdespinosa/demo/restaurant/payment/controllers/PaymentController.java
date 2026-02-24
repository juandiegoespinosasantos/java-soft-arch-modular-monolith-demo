package com.jdespinosa.demo.restaurant.payment.controllers;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.payment.model.dto.PaymentDTO;
import com.jdespinosa.demo.restaurant.payment.model.dto.PaymentRequestDTO;
import com.jdespinosa.demo.restaurant.payment.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Payment controller implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController implements IPaymentController {

    private final IPaymentService service;

    @Autowired
    public PaymentController(IPaymentService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentDTO>> findAll() {
        List<PaymentDTO> payments = service.findAll();

        return ResponseEntity.ok(payments);
    }

    @Override
    @GetMapping(path = "/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDTO> findByOrder(@PathVariable("orderId") Long orderId) {
        Optional<PaymentDTO> opt = service.findByOrderId(orderId);
        if (opt.isEmpty()) throw new NotFoundException("Order", orderId.toString());

        return ResponseEntity.ok(opt.get());
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDTO> process(@RequestBody PaymentRequestDTO requestBody) {
        PaymentDTO processed = service.processPayment(requestBody);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(processed);
    }
}