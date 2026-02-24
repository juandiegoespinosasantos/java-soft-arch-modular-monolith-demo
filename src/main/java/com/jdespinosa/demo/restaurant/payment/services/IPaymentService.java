package com.jdespinosa.demo.restaurant.payment.services;

import com.jdespinosa.demo.restaurant.payment.model.dto.PaymentDTO;
import com.jdespinosa.demo.restaurant.payment.model.dto.PaymentRequestDTO;

import java.util.List;
import java.util.Optional;

/**
 * Payment service.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
public interface IPaymentService {

    List<PaymentDTO> findAll();

    Optional<PaymentDTO> findByOrderId(Long orderId);

    PaymentDTO processPayment(PaymentRequestDTO requestBody);
}