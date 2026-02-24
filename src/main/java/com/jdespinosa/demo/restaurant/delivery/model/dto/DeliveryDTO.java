package com.jdespinosa.demo.restaurant.delivery.model.dto;

import com.jdespinosa.demo.restaurant.delivery.model.enums.DeliveryStatuses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Delivery data transfer object.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2193786259595275935L;

    private Long id;
    private Long orderId;
    private String waiterName;
    private DeliveryStatuses status;
    private Long deliveredAt;
}