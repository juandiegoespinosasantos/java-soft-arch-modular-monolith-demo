package com.jdespinosa.demo.restaurant.placeorder.controllers;

import com.jdespinosa.demo.restaurant.commons.controllers.IBasicController;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderRequestDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Order controller.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
public interface IOrderController extends IBasicController<Long, OrderDTO, OrderRequestDTO> {

    ResponseEntity<List<OrderDTO>> findByStatus(OrderStatuses status);

    ResponseEntity<OrderDTO> placeOrder(OrderRequestDTO req);

    ResponseEntity<OrderDTO> updateStatus(Long id, OrderStatuses status);
}