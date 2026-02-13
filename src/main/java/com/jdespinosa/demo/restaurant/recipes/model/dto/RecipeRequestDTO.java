package com.jdespinosa.demo.restaurant.recipes.model.dto;

/**
 * Recipe request body.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
public record RecipeRequestDTO(String name, String description, double price, int preparationTimeMinutes) {
}