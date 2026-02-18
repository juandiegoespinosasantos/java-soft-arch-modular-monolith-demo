package com.jdespinosa.demo.restaurant.placeorder.model.repositories;

import com.jdespinosa.demo.restaurant.placeorder.model.entities.Order;
import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Order repository.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatuses status);

    List<Order> findByCustomerEmailIgnoreCase(String email);

    List<Order> findAllByOrderByCreatedAtDesc();
}