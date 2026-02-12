package com.jdespinosa.demo.restaurant.inventory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Inventory data transfer object.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 10, 2026
 * @since 17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7286652251455623424L;

    private Long id;
    private String ingredientName;
    private double quantity;
    private String unit;
    private double minStock;

    public boolean isLowStock() {
        return quantity <= minStock;
    }
}