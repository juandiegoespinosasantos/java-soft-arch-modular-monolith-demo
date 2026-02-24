package com.jdespinosa.demo.restaurant.prepareorder.model.repositories;

import com.jdespinosa.demo.restaurant.prepareorder.model.entities.OrderPreparation;
import com.jdespinosa.demo.restaurant.prepareorder.model.enums.OrderPreparationStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Order preparation repository.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@Repository
public interface OrderPreparationRepository extends JpaRepository<OrderPreparation, Long> {

    Optional<OrderPreparation> findByOrderId(Long orderId);

    List<OrderPreparation> findByStatus(OrderPreparationStatuses status);
}