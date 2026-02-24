package com.jdespinosa.demo.restaurant.prepareorder.model.dto;

import com.jdespinosa.demo.restaurant.prepareorder.model.enums.OrderPreparationStatuses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Order preparation data transfer object.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPreparationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6392766000194540202L;

    private Long id;
    private Long orderId;
    private OrderPreparationStatuses status;
    private String chefNotes;
    private Long startedAt;
    private Long completedAt;
}