package com.jdespinosa.demo.restaurant.prepareorder.utils.adapters;

import com.jdespinosa.demo.restaurant.prepareorder.model.dto.OrderPreparationDTO;
import com.jdespinosa.demo.restaurant.prepareorder.model.entities.OrderPreparation;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Order preparation domain adapter.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@UtilityClass
public final class OrderPreparationAdapter {

    /**
     * Transforms entity into DTO.
     *
     * @param entity Entity.
     * @return Order preparation data transfer object.
     */
    public OrderPreparationDTO transform(final OrderPreparation entity) {
        return OrderPreparationDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .status(entity.getStatus())
                .chefNotes(entity.getChefNotes())
                .startedAt(toMillis(entity.getStartedAt()))
                .completedAt(toMillis(entity.getCompletedAt()))
                .build();
    }

    /**
     * Transforms a list of entities into a list of DTO.
     *
     * @param entities List of entities.
     * @return List of Order preparation data transfer objects.
     */
    public List<OrderPreparationDTO> transform(final List<OrderPreparation> entities) {
        return entities.stream()
                .map(OrderPreparationAdapter::transform)
                .toList();
    }

    private Long toMillis(final LocalDateTime datetime) {
        return (datetime == null) ? null : datetime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}