package com.jdespinosa.demo.restaurant.prepareorder.services;

import com.jdespinosa.demo.restaurant.prepareorder.model.dto.OrderPreparationDTO;
import com.jdespinosa.demo.restaurant.prepareorder.model.enums.OrderPreparationStatuses;

import java.util.List;
import java.util.Optional;

/**
 * Order preparation service.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
public interface IOrderPreparationService {

    List<OrderPreparationDTO> findAll();

    List<OrderPreparationDTO> findByStatus(OrderPreparationStatuses status);

    Optional<OrderPreparationDTO> findByOrderId(Long orderId);

    OrderPreparationDTO startPreparation(Long orderId);

    OrderPreparationDTO markReady(Long orderId, String chefNotes);
}