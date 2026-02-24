package com.jdespinosa.demo.restaurant.delivery.model.repositories;

import com.jdespinosa.demo.restaurant.delivery.model.entities.Delivery;
import com.jdespinosa.demo.restaurant.delivery.model.enums.DeliveryStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Delivery repository.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByOrderId(Long orderId);

    List<Delivery> findByStatus(DeliveryStatuses status);
}