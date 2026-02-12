package com.jdespinosa.demo.restaurant.inventory.utils.adapters;

import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.entities.Inventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

/**
 * Unit testing for {@link InventoryAdapter}
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 11, 2026
 * @since 17
 */
class InventoryAdapterTest {

    @Test
    void givenDTO_whenTransform_thenReturnEntity() {
        // given
        InventoryRequestDTO dto = new InventoryRequestDTO("Test ingredient", 100, "Units", 50);

        // when
        Inventory actual = InventoryAdapter.transform(dto);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(dto.ingredientName(), actual.getIngredientName());
        Assertions.assertEquals(dto.quantity(), actual.getQuantity());
        Assertions.assertEquals(dto.unit(), actual.getUnit());
        Assertions.assertEquals(dto.minStock(), actual.getMinStock());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 50, 100, 200})
    void givenEntity_whenTransform_thenReturnDTO(double minStock) {
        // given
        Inventory entity = buildEntity(minStock);

        // when
        InventoryDTO actual = InventoryAdapter.transform(entity);

        // then
        validate(entity, actual, minStock);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 50.0, 100.0, 200.0})
    void givenListOfEntities_whenTransform_thenReturnListOfDTOs(double minStock) {
        // given
        List<Inventory> entities = List.of(buildEntity(minStock));

        // when
        List<InventoryDTO> actualList = InventoryAdapter.transform(entities);

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertFalse(actualList.isEmpty());
        Assertions.assertEquals(entities.size(), actualList.size());

        Inventory entity = entities.get(0);
        InventoryDTO actual = actualList.get(0);
        validate(entity, actual, minStock);
    }

    private Inventory buildEntity(double minStock) {
        return Inventory.builder()
                .id(33L)
                .ingredientName("Test ingredient")
                .quantity(100.0)
                .unit("Units")
                .minStock(minStock)
                .build();
    }

    private void validate(Inventory expected, InventoryDTO actual, double minStock) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getIngredientName(), actual.getIngredientName());
        Assertions.assertEquals(expected.getQuantity(), actual.getQuantity());
        Assertions.assertEquals(expected.getUnit(), actual.getUnit());
        Assertions.assertEquals(expected.getMinStock(), actual.getMinStock());
        Assertions.assertEquals((actual.getQuantity() <= minStock), actual.isLowStock());
    }
}