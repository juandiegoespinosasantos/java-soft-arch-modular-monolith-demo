package com.jdespinosa.demo.restaurant.payment.model.dto;

import com.jdespinosa.demo.restaurant.payment.model.enums.PaymentMethods;
import com.jdespinosa.demo.restaurant.payment.model.enums.PaymentStatuses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Payment data transfer object.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3308740904622047099L;

    private Long id;
    private Long orderId;
    private double amount;
    private PaymentMethods method;
    private PaymentStatuses status;
    private Long paidAt;
}