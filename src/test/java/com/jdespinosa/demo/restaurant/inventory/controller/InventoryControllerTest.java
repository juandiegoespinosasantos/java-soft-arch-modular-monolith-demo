package com.jdespinosa.demo.restaurant.inventory.controller;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.inventory.model.dto.AdjustInventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.services.IInventoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Unit testing for {@link InventoryController}
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 11, 2026
 * @since 17
 */
@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private IInventoryService mockService;

    private InventoryController controller;

    @BeforeEach
    void setUp() {
        controller = new InventoryController(mockService);
    }

    @Test
    void givenExistentInventories_whenFindAll_thenReturnResponseEntityOK() {
        // given
        List<InventoryDTO> inventories = List.of(
                buildDTO(1L, "Inventory A"),
                buildDTO(2L, "Inventory B"),
                buildDTO(3L, "Inventory C"));
        Mockito.when(mockService.findAll()).thenReturn(inventories);

        // when
        ResponseEntity<List<InventoryDTO>> actualResponse = controller.findAll();

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        List<InventoryDTO> actualList = actualResponse.getBody();
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(inventories.size(), actualList.size());

        InventoryDTO entity;
        InventoryDTO actual;

        for (int i = 0; i < actualList.size(); i++) {
            entity = inventories.get(i);
            actual = actualList.get(i);

            validate(entity, actual);
        }

        Mockito.verify(mockService).findAll();
    }

    @Test
    void givenNoExistentInventories_whenFindAll_thenReturnResponseEntityNotFound() {
        // given
        Mockito.when(mockService.findAll()).thenReturn(Collections.emptyList());

        // when
        ResponseEntity<List<InventoryDTO>> actualResponse = controller.findAll();

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        Mockito.verify(mockService).findAll();
    }

    @Test
    void givenExistentId_whenFind_thenReturnResponseEntityOK() {
        // given
        long id = 1L;

        InventoryDTO inventory = buildDTO(id, "Inventory A");
        Mockito.when(mockService.find(id)).thenReturn(Optional.of(inventory));

        // when
        ResponseEntity<InventoryDTO> actualResponse = controller.findById(id);

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        InventoryDTO actual = actualResponse.getBody();
        validate(inventory, actual);

        Mockito.verify(mockService).find(id);
    }

    @Test
    void givenNonExistentId_whenFind_thenReturnResponseEntityNotFound() {
        // given
        long id = 1L;

        Mockito.when(mockService.find(id)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(NotFoundException.class, () -> controller.findById(id), "Entity Recipe.id=1 not found");

        Mockito.verify(mockService).find(id);
    }

    @Test
    void givenExistentLowStockInventories_whenFindLowStock_thenReturnResponseEntityOK() {
        // given
        List<InventoryDTO> lowStocks = List.of(
                buildDTO(1L, "Inventory A", 10, 100),
                buildDTO(2L, "Inventory B", 30, 30),
                buildDTO(3L, "Inventory C", 22, 75));
        Mockito.when(mockService.findLowStock()).thenReturn(lowStocks);

        // when
        ResponseEntity<List<InventoryDTO>> actualResponse = controller.lowStock();

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        List<InventoryDTO> actualList = actualResponse.getBody();
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(lowStocks.size(), actualList.size());

        InventoryDTO entity;
        InventoryDTO actual;

        for (int i = 0; i < actualList.size(); i++) {
            entity = lowStocks.get(i);
            actual = actualList.get(i);

            validate(entity, actual);
        }

        Mockito.verify(mockService).findLowStock();
    }

    @Test
    void givenNoExistentLowStockInventories_whenFindLowStock_thenReturnResponseEntityNotFound() {
        // given
        Mockito.when(mockService.findLowStock()).thenReturn(Collections.emptyList());

        // when
        ResponseEntity<List<InventoryDTO>> actualResponse = controller.lowStock();

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        Mockito.verify(mockService).findLowStock();
    }

    @Test
    void givenRequestBody_whenCreate_thenReturnResponseEntityCreated() {
        // given
        InventoryRequestDTO requestBody = new InventoryRequestDTO("Test ingredient", 100, "Units", 50);

        InventoryDTO created = buildDTO(1L, requestBody);
        Mockito.when(mockService.create(requestBody)).thenReturn(created);

        // when
        ResponseEntity<InventoryDTO> actualResponse = controller.create(requestBody);

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());

        InventoryDTO actual = actualResponse.getBody();
        validate(created, actual);

        Mockito.verify(mockService).create(requestBody);
    }

    @Test
    void givenRequestBody_whenUpdate_thenReturnResponseEntityOK() {
        // given
        long id = 1L;
        InventoryRequestDTO requestBody = new InventoryRequestDTO("New test ingredient", 101, "Uni", 51);

        InventoryDTO updated = buildDTO(id, requestBody);
        Mockito.when(mockService.update(id, requestBody)).thenReturn(updated);

        // when
        ResponseEntity<InventoryDTO> actualResponse = controller.update(id, requestBody);

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        InventoryDTO actual = actualResponse.getBody();
        validate(updated, actual);

        Mockito.verify(mockService).update(id, requestBody);
    }

    @Test
    void givenExistentId_whenAdjust_thenReturnUpdatedDTO() {
        // given
        long id = 1L;
        AdjustInventoryRequestDTO requestBody = new AdjustInventoryRequestDTO(666L, "This is a test");

        InventoryDTO adjusted = buildDTO(id, "Test ingredient");
        adjusted.setQuantity(adjusted.getQuantity() + requestBody.quantity());
        Mockito.when(mockService.adjust(id, requestBody)).thenReturn(adjusted);

        // when
        ResponseEntity<InventoryDTO> actualResponse = controller.adjust(id, requestBody);

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        InventoryDTO actual = actualResponse.getBody();
        validate(adjusted, actual);

        Mockito.verify(mockService).adjust(id, requestBody);
    }

    private InventoryDTO buildDTO(long id, String name, double quantity, double minStock) {
        return InventoryDTO.builder()
                .id(id)
                .ingredientName(name)
                .quantity(quantity)
                .unit("Units")
                .minStock(minStock)
                .build();
    }

    private InventoryDTO buildDTO(long id, String name) {
        return buildDTO(id, name, 100.0, 50.0);
    }

    private InventoryDTO buildDTO(Long id, InventoryRequestDTO dto) {
        double quantity = dto.quantity();
        double minStock = dto.minStock();

        return InventoryDTO.builder()
                .id(id)
                .ingredientName(dto.ingredientName())
                .quantity(quantity)
                .unit(dto.unit())
                .minStock(minStock)
                .build();
    }

    private void validate(InventoryDTO expected, InventoryDTO actual) {
        double actualQuantity = actual.getQuantity();
        double actualMinStock = actual.getMinStock();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getIngredientName(), actual.getIngredientName());
        Assertions.assertEquals(expected.getQuantity(), actualQuantity);
        Assertions.assertEquals(expected.getUnit(), actual.getUnit());
        Assertions.assertEquals(expected.getMinStock(), actualMinStock);
        Assertions.assertEquals((actualQuantity <= actualMinStock), actual.isLowStock());
    }
}