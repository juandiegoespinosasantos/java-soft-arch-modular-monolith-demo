package com.jdespinosa.demo.restaurant.delivery.controllers;

import com.jdespinosa.demo.restaurant.delivery.model.dto.DeliveryDTO;
import com.jdespinosa.demo.restaurant.delivery.model.dto.DeliveryRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Delivery controller.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
public interface IDeliveryController {

    ResponseEntity<List<DeliveryDTO>> findAll();

    ResponseEntity<List<DeliveryDTO>> findPending();

    ResponseEntity<DeliveryDTO> deliver(DeliveryRequestDTO requestBody);
}