package com.jdespinosa.demo.restaurant.prepareorder.model.entities;

import com.jdespinosa.demo.restaurant.prepareorder.model.enums.OrderPreparationStatuses;
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
 * Order preparation entity.
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
@Table(name = "order_preparation")
public class OrderPreparation implements Serializable {

    @Serial
    private static final long serialVersionUID = 6268130106953520312L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderPreparationStatuses status;

    @Column(name = "chef_notes")
    private String chefNotes;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}