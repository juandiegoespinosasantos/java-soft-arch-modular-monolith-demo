package com.jdespinosa.demo.restaurant.payment.model.dto;

import com.jdespinosa.demo.restaurant.payment.model.enums.PaymentMethods;

/**
 * Payment request body.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
public record PaymentRequestDTO(Long orderId, PaymentMethods method) {
}