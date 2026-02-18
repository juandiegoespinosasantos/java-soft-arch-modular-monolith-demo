package com.jdespinosa.demo.restaurant.placeorder.model.dto;

/**
 * Item request body.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
public record OrderItemRequestDTO(Long recipeId, int quantity) {
}