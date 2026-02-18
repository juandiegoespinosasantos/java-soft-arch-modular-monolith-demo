package com.jdespinosa.demo.restaurant.inventory.utils.adapters;

import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.entities.Inventory;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * Inventory domain adapter.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 10, 2026
 * @since 17
 */
@UtilityClass
public final class InventoryAdapter {

    /**
     * Transforms DTO into entity.
     *
     * @param dto Data transfer object.
     * @return Inventory entity.
     */
    public Inventory transform(final InventoryRequestDTO dto) {
        return Inventory.builder()
                .ingredientName(dto.ingredientName())
                .quantity(dto.quantity())
                .unit(dto.unit())
                .minStock(dto.minStock())
                .build();
    }

    /**
     * Transforms entity into DTO.
     *
     * @param entity Entity.
     * @return Inventory data transfer object.
     */
    public InventoryDTO transform(final Inventory entity) {
        double quantity = entity.getQuantity();
        double minStock = entity.getMinStock();

        return InventoryDTO.builder()
                .id(entity.getId())
                .ingredientName(entity.getIngredientName())
                .quantity(quantity)
                .unit(entity.getUnit())
                .minStock(minStock)
                .build();
    }

    /**
     * Transforms a list of entities into a list of DTO.
     *
     * @param entities List of entities.
     * @return List of Inventory data transfer objects.
     */
    public List<InventoryDTO> transform(final List<Inventory> entities) {
        return entities.stream()
                .map(InventoryAdapter::transform)
                .toList();
    }
}