package com.jdespinosa.demo.restaurant.inventory.model.dto;

/**
 * Adjust inventory request body.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 10, 2026
 * @since 17
 */
public record AdjustInventoryRequestDTO(double quantity, String reason) {
}