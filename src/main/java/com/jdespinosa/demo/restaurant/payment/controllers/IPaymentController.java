package com.jdespinosa.demo.restaurant.payment.controllers;

import com.jdespinosa.demo.restaurant.payment.model.dto.PaymentDTO;
import com.jdespinosa.demo.restaurant.payment.model.dto.PaymentRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Payment controller.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
public interface IPaymentController {

    ResponseEntity<List<PaymentDTO>> findAll();

    ResponseEntity<PaymentDTO> findByOrder(Long orderId);

    ResponseEntity<PaymentDTO> process(PaymentRequestDTO requestBody);
}