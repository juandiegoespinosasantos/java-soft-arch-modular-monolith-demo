package com.jdespinosa.demo.restaurant.prepareorder.controllers;

import com.jdespinosa.demo.restaurant.prepareorder.model.dto.OrderPreparationDTO;
import com.jdespinosa.demo.restaurant.prepareorder.model.enums.OrderPreparationStatuses;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Order preparation controller.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
public interface IOrderPreparationController {

    ResponseEntity<List<OrderPreparationDTO>> findAll();

    ResponseEntity<List<OrderPreparationDTO>> findByStatus(OrderPreparationStatuses status);

    ResponseEntity<OrderPreparationDTO> findByOrder(Long orderId);

    ResponseEntity<OrderPreparationDTO> start(Long orderId);

    ResponseEntity<OrderPreparationDTO> markReady(Long orderId, String chefNotes);
}