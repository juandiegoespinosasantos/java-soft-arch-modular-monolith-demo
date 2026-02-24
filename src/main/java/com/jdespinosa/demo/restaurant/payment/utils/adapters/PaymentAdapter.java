package com.jdespinosa.demo.restaurant.payment.utils.adapters;

import com.jdespinosa.demo.restaurant.payment.model.dto.PaymentDTO;
import com.jdespinosa.demo.restaurant.payment.model.entities.Payment;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Payment domain adapter.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@UtilityClass
public final class PaymentAdapter {

    /**
     * Transforms entity into DTO.
     *
     * @param entity Entity.
     * @return Payment data transfer object.
     */
    public PaymentDTO transform(final Payment entity) {
        return PaymentDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .amount(entity.getAmount())
                .method(entity.getMethod())
                .status(entity.getStatus())
                .paidAt(toMillis(entity.getPaidAt()))
                .build();
    }

    /**
     * Transforms a list of entities into a list of DTO.
     *
     * @param entities List of entities.
     * @return List of Payment data transfer objects.
     */
    public List<PaymentDTO> transform(final List<Payment> entities) {
        return entities.stream()
                .map(PaymentAdapter::transform)
                .toList();
    }

    private long toMillis(final LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}