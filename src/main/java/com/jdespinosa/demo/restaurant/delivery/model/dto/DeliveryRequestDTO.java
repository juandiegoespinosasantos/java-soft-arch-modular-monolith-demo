package com.jdespinosa.demo.restaurant.delivery.model.dto;

/**
 * Delivery request body.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
public record DeliveryRequestDTO(Long orderId, String waiterName) {
}