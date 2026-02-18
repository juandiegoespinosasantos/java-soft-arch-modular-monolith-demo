package com.jdespinosa.demo.restaurant.placeorder.utils.adapters;

import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderItemDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderRequestDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.entities.Order;
import com.jdespinosa.demo.restaurant.placeorder.model.entities.OrderItem;
import lombok.experimental.UtilityClass;

import java.time.ZoneOffset;
import java.util.List;

/**
 * Order domain adapter.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
@UtilityClass
public final class OrderAdapter {

    /**
     * Transforms DTO into entity.
     *
     * @param dto Data transfer object.
     * @return Order entity.
     */
    public Order transform(final OrderRequestDTO dto) {
        return Order.builder()
                .customerName(dto.customerName())
                .customerEmail(dto.customerEmail())
                .tableNumber(dto.tableNumber())
                .build();
    }

    /**
     * Transforms entity into DTO.
     *
     * @param entity Entity.
     * @return Order data transfer object.
     */
    public OrderDTO transform(final Order entity) {
        List<OrderItemDTO> items = entity.getItems()
                .stream()
                .map(OrderAdapter::transform)
                .toList();

        return OrderDTO.builder()
                .id(entity.getId())
                .customerName(entity.getCustomerName())
                .customerEmail(entity.getCustomerEmail())
                .tableNumber(entity.getTableNumber())
                .status(entity.getStatus())
                .total(entity.getTotal())
                .items(items)
                .createdAt(getCreatedAt(entity))
                .build();
    }

    /**
     * Transforms a list of entities into a list of DTO.
     *
     * @param entities List of entities.
     * @return List of Order data transfer objects.
     */
    public List<OrderDTO> transform(final List<Order> entities) {
        return entities.stream()
                .map(OrderAdapter::transform)
                .toList();
    }

    private OrderItemDTO transform(final OrderItem entity) {
        return OrderItemDTO.builder()
                .id(entity.getId())
                .recipeId(entity.getRecipeId())
                .recipeName(entity.getRecipeName())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .subtotal(entity.getSubtotal())
                .build();
    }

    private long getCreatedAt(final Order entity) {
        return entity.getCreatedAt()
                .toInstant(ZoneOffset.UTC)
                .toEpochMilli();
    }
}