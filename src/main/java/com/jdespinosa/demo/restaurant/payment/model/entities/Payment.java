package com.jdespinosa.demo.restaurant.payment.model.entities;

import com.jdespinosa.demo.restaurant.payment.model.enums.PaymentMethods;
import com.jdespinosa.demo.restaurant.payment.model.enums.PaymentStatuses;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Payment entity.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    @Serial
    private static final long serialVersionUID = -2702624086598630795L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "amount")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private PaymentMethods method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatuses status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}