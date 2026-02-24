package com.jdespinosa.demo.restaurant.delivery.services;

import com.jdespinosa.demo.restaurant.delivery.model.dto.DeliveryDTO;
import com.jdespinosa.demo.restaurant.delivery.model.dto.DeliveryRequestDTO;

import java.util.List;

/**
 * Delivery service implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
public interface IDeliveryService {

    List<DeliveryDTO> findAll();

    List<DeliveryDTO> findPending();

    DeliveryDTO deliver(DeliveryRequestDTO requestBody);
}