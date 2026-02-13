package com.jdespinosa.demo.restaurant.recipes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Recipe data transfer object.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -110117848309373137L;

    private Long id;
    private String name;
    private String description;
    private double price;
    private int preparationTimeMinutes;
    private boolean active;
}