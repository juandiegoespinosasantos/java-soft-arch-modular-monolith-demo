package com.jdespinosa.demo.restaurant.placeorder.model.entities;

import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Order entity.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 5702896774019077936L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "table_number")
    private int tableNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatuses status;

    @Column(name = "total")
    private double total;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    public List<OrderItem> getItems() {
        if (items == null) items = new LinkedList<>();
        return items;
    }

    public void addItem(OrderItem item) {
        getItems().add(item);
    }
}