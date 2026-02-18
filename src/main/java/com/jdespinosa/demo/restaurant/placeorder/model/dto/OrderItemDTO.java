package com.jdespinosa.demo.restaurant.placeorder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Order item data transfer object.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8791043320329377254L;

    private Long id;
    private Long recipeId;
    private String recipeName;
    private int quantity;
    private double unitPrice;
    private double subtotal;
}