package com.jdespinosa.demo.restaurant.inventory.model.dto;

/**
 * Inventory request body.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 10, 2026
 * @since 17
 */
public record InventoryRequestDTO(String ingredientName, double quantity, String unit, double minStock) {
}