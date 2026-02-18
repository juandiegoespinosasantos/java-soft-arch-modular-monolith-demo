package com.jdespinosa.demo.restaurant.placeorder.model.dto;

import java.util.List;

/**
 * Order request body.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
public record OrderRequestDTO(String customerName, String customerEmail, int tableNumber,
                              List<OrderItemRequestDTO> items) {
}