package com.jdespinosa.demo.restaurant.placeorder.services;

import com.jdespinosa.demo.restaurant.commons.services.IBasicService;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderRequestDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;

import java.util.List;

/**
 * Order service.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
public interface IOrderService extends IBasicService<Long, OrderDTO, OrderRequestDTO> {

    List<OrderDTO> findByStatus(OrderStatuses status);

    OrderDTO placeOrder(OrderRequestDTO requestBody);

    OrderDTO updateStatus(Long id, OrderStatuses newStatus);
}