package com.jdespinosa.demo.restaurant.placeorder.utils.adapters;

import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderItemDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderRequestDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.entities.Order;
import com.jdespinosa.demo.restaurant.placeorder.model.entities.OrderItem;
import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

/**
 * Unit testing for {@link OrderAdapter}
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
class OrderAdapterTest {

    @Test
    void givenDTO_whenTransform_thenReturnEntity() {
        // given
        OrderRequestDTO dto = new OrderRequestDTO("John Doe", "johndoe@example.com", 33, Collections.emptyList());

        // when
        Order actual = OrderAdapter.transform(dto);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(dto.customerName(), actual.getCustomerName());
        Assertions.assertEquals(dto.customerEmail(), actual.getCustomerEmail());
        Assertions.assertEquals(dto.tableNumber(), actual.getTableNumber());

        List<OrderItem> actualItems = actual.getItems();
        Assertions.assertNotNull(actualItems);
        Assertions.assertTrue(actualItems.isEmpty());
    }

    @Test
    void givenEntity_whenTransform_thenReturnDTO() {
        // given
        Order entity = buildEntity();

        // when
        OrderDTO actual = OrderAdapter.transform(entity);

        // then
        validate(entity, actual);
    }

    @Test
    void givenListOfEntities_whenTransform_thenReturnListOfDTOs() {
        // given
        List<Order> entities = List.of(buildEntity());

        // when
        List<OrderDTO> actualList = OrderAdapter.transform(entities);

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertFalse(actualList.isEmpty());
        Assertions.assertEquals(entities.size(), actualList.size());

        Order entity = entities.get(0);
        OrderDTO actual = actualList.get(0);
        validate(entity, actual);
    }

    private Order buildEntity() {
        List<OrderItem> items = List.of(
                buildEntity(1L, 10L, "Recipe A", 2, 1_000.0),
                buildEntity(2L, 20L, "Recipe B", 4, 3_240.0),
                buildEntity(3L, 30L, "Recipe B", 10, 800.0));
        double total = items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();

        return Order.builder()
                .id(1L)
                .customerName("John Doe")
                .customerEmail("johndoe@example.com")
                .tableNumber(33)
                .status(OrderStatuses.PENDING)
                .total(total)
                .items(items)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private OrderItem buildEntity(Long id, Long recipeId, String recipeName, int qty, double unitPrice) {
        double subtotal = qty * unitPrice;

        return OrderItem.builder()
                .id(id)
                .recipeId(recipeId)
                .recipeName(recipeName)
                .quantity(qty)
                .unitPrice(unitPrice)
                .subtotal(subtotal)
                .build();
    }

    private void validate(Order expected, OrderDTO actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getCustomerName(), actual.getCustomerName());
        Assertions.assertEquals(expected.getCustomerEmail(), actual.getCustomerEmail());
        Assertions.assertEquals(expected.getTableNumber(), actual.getTableNumber());
        Assertions.assertEquals(expected.getStatus(), actual.getStatus());
        Assertions.assertEquals(expected.getTotal(), actual.getTotal());
        Assertions.assertEquals(expected.getCreatedAt().toInstant(ZoneOffset.UTC).toEpochMilli(), actual.getCreatedAt());

        List<OrderItem> expectedItems = expected.getItems();
        List<OrderItemDTO> actualItems = actual.getItems();
        Assertions.assertNotNull(actualItems);
        Assertions.assertFalse(actualItems.isEmpty());
        Assertions.assertEquals(expectedItems.size(), actualItems.size());

        OrderItem item;
        OrderItemDTO actualItem;

        for (int i = 0; i < actualItems.size(); i++) {
            item = expectedItems.get(i);
            actualItem = actualItems.get(i);

            Assertions.assertNotNull(actualItem);
            Assertions.assertEquals(item.getId(), actualItem.getId());
            Assertions.assertEquals(item.getRecipeId(), actualItem.getRecipeId());
            Assertions.assertEquals(item.getRecipeName(), actualItem.getRecipeName());
            Assertions.assertEquals(item.getQuantity(), actualItem.getQuantity());
            Assertions.assertEquals(item.getUnitPrice(), actualItem.getUnitPrice());
            Assertions.assertEquals(item.getSubtotal(), actualItem.getSubtotal());
        }
    }
}