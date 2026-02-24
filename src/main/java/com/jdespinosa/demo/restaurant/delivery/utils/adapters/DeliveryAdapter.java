package com.jdespinosa.demo.restaurant.delivery.utils.adapters;

import com.jdespinosa.demo.restaurant.delivery.model.dto.DeliveryDTO;
import com.jdespinosa.demo.restaurant.delivery.model.entities.Delivery;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Delivery domain adapter.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@UtilityClass
public final class DeliveryAdapter {

    /**
     * Transforms entity into DTO.
     *
     * @param entity Entity.
     * @return Delivery preparation data transfer object.
     */
    public DeliveryDTO transform(final Delivery entity) {
        return DeliveryDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .waiterName(entity.getWaiterName())
                .status(entity.getStatus())
                .deliveredAt(toMillis(entity.getDeliveredAt()))
                .build();
    }

    /**
     * Transforms a list of entities into a list of DTO.
     *
     * @param entities List of entities.
     * @return List of Delivery preparation data transfer objects.
     */
    public List<DeliveryDTO> transform(final List<Delivery> entities) {
        return entities.stream()
                .map(DeliveryAdapter::transform)
                .collect(Collectors.toList());
    }

    private Long toMillis(final LocalDateTime datetime) {
        return (datetime == null) ? null : datetime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}