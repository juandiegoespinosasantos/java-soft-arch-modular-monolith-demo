package com.jdespinosa.demo.restaurant.inventory.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.inventory.model.dto.AdjustInventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.entities.Inventory;
import com.jdespinosa.demo.restaurant.inventory.model.repositories.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Unit testing for {@link InventoryService}
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 11, 2026
 * @since 17
 */
@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository mockRepository;

    private InventoryService service;

    @BeforeEach
    void setUp() {
        service = new InventoryService(mockRepository);
    }

    @Test
    void givenExistentInventories_whenFindAll_thenReturnListOfDTOs() {
        // given
        List<Inventory> inventories = List.of(
                buildEntity(1L, "Inventory A"),
                buildEntity(2L, "Inventory B"),
                buildEntity(3L, "Inventory C"));
        Mockito.when(mockRepository.findAll()).thenReturn(inventories);

        // when
        List<InventoryDTO> actualList = service.findAll();

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(inventories.size(), actualList.size());

        Inventory entity;
        InventoryDTO actual;

        for (int i = 0; i < actualList.size(); i++) {
            entity = inventories.get(i);
            actual = actualList.get(i);

            validate(entity, actual);
        }

        Mockito.verify(mockRepository).findAll();
    }

    @Test
    void givenNoExistentInventories_whenFindAll_thenReturnEmptyList() {
        // given
        Mockito.when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<InventoryDTO> actualList = service.findAll();

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertTrue(actualList.isEmpty());

        Mockito.verify(mockRepository).findAll();
    }

    @Test
    void givenExistentId_whenFind_thenReturnOptionalDTO() {
        // given
        long id = 1L;

        Inventory inventory = buildEntity(id, "Inventory A");
        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.of(inventory));

        // when
        Optional<InventoryDTO> opt = service.find(id);

        // then
        Assertions.assertNotNull(opt);
        Assertions.assertTrue(opt.isPresent());

        InventoryDTO actual = opt.get();

        validate(inventory, actual);

        Mockito.verify(mockRepository).findById(id);
    }

    @Test
    void givenNonExistentId_whenFind_thenReturnOptionalEmpty() {
        // given
        long id = 1L;

        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Optional<InventoryDTO> opt = service.find(id);

        // then
        Assertions.assertNotNull(opt);
        Assertions.assertTrue(opt.isEmpty());

        Mockito.verify(mockRepository).findById(id);
    }

    @Test
    void givenExistentLowStockInventories_whenFindLowStock_thenReturnListOfDTOs() {
        // given
        List<Inventory> lowStocks = List.of(
                buildEntity(1L, "Inventory A", 10, 100),
                buildEntity(2L, "Inventory B", 30, 30),
                buildEntity(3L, "Inventory C", 22, 75));
        Mockito.when(mockRepository.findLowStock()).thenReturn(lowStocks);

        // when
        List<InventoryDTO> actualList = service.findLowStock();

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(lowStocks.size(), actualList.size());

        Inventory entity;
        InventoryDTO actual;

        for (int i = 0; i < actualList.size(); i++) {
            entity = lowStocks.get(i);
            actual = actualList.get(i);

            validate(entity, actual);
        }

        Mockito.verify(mockRepository).findLowStock();
    }

    @Test
    void givenNoExistentLowStockInventories_whenFindLowStock_thenReturnListOfDTOs() {
        // given
        Mockito.when(mockRepository.findLowStock()).thenReturn(Collections.emptyList());

        // when
        List<InventoryDTO> actualList = service.findLowStock();

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertTrue(actualList.isEmpty());

        Mockito.verify(mockRepository).findLowStock();
    }

    @Test
    void givenRequestBody_whenCreate_thenReturnCreatedDTO() {
        // given
        InventoryRequestDTO requestBody = new InventoryRequestDTO("Test ingredient", 100, "Units", 50);
        Inventory toCreate = buildEntity(null, requestBody);
        Inventory created = buildEntity(1L, requestBody);
        Mockito.when(mockRepository.save(toCreate)).thenReturn(created);

        // when
        InventoryDTO actual = service.create(requestBody);

        // then
        validate(created, actual);

        Mockito.verify(mockRepository).save(toCreate);
    }

    @Test
    void givenExistentId_whenUpdate_thenReturnUpdatedDTO() {
        // given
        long id = 1L;
        InventoryRequestDTO requestBody = new InventoryRequestDTO("New test ingredient", 101, "Uni", 51);

        Inventory pivot = buildEntity(id, "Test ingredient");
        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.of(pivot));

        Inventory updated = buildEntity(id, requestBody);
        Mockito.when(mockRepository.save(pivot)).thenReturn(pivot);

        // when
        InventoryDTO actual = service.update(id, requestBody);

        // then
        validate(updated, actual);

        Mockito.verify(mockRepository).save(pivot);
    }

    @Test
    void givenNonExistentId_whenUpdate_thenThrowNotFoundException() {
        // given
        long id = 1L;
        InventoryRequestDTO requestBody = new InventoryRequestDTO("New test ingredient", 101, "Uni", 51);

        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(NotFoundException.class, () -> service.update(id, requestBody), "Entity Inventory.id=1 not found");

        Mockito.verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void givenExistentId_whenAdjust_thenReturnUpdatedDTO() {
        // given
        long id = 1L;
        AdjustInventoryRequestDTO requestBody = new AdjustInventoryRequestDTO(666L, "This is a test");

        Inventory pivot = buildEntity(id, "Test ingredient");
        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.of(pivot));

        pivot.setQuantity(pivot.getQuantity() + requestBody.quantity());
        Mockito.when(mockRepository.save(pivot)).thenReturn(pivot);

        // when
        InventoryDTO actual = service.adjust(id, requestBody);

        // then
        validate(pivot, actual);

        Mockito.verify(mockRepository).save(pivot);
    }

    @Test
    void givenNonExistentId_whenAdjust_thenThrowNotFoundException() {
        // given
        long id = 1L;
        AdjustInventoryRequestDTO requestBody = new AdjustInventoryRequestDTO(666L, "This is a test");

        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(NotFoundException.class, () -> service.adjust(id, requestBody), "Entity Inventory.id=1 not found");

        Mockito.verifyNoMoreInteractions(mockRepository);
    }

    private Inventory buildEntity(long id, String name, double quantity, double minStock) {
        return Inventory.builder()
                .id(id)
                .ingredientName(name)
                .quantity(quantity)
                .unit("Units")
                .minStock(minStock)
                .build();
    }

    private Inventory buildEntity(long id, String name) {
        return buildEntity(id, name, 100.0, 50.0);
    }

    private Inventory buildEntity(Long id, InventoryRequestDTO dto) {
        return Inventory.builder()
                .id(id)
                .ingredientName(dto.ingredientName())
                .quantity(dto.quantity())
                .unit(dto.unit())
                .minStock(dto.minStock())
                .build();
    }

    private void validate(Inventory expected, InventoryDTO actual) {
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