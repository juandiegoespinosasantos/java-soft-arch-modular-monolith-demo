package com.jdespinosa.demo.restaurant.delivery.model.entities;

import com.jdespinosa.demo.restaurant.delivery.model.enums.DeliveryStatuses;
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
 * Delivery entity.
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
@Table(name = "delivery")
public class Delivery implements Serializable {

    @Serial
    private static final long serialVersionUID = 5194841808218244135L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "waiter_name")
    private String waiterName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatuses status;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
}