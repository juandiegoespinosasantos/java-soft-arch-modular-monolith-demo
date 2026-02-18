package com.jdespinosa.demo.restaurant.placeorder.controllers;

import com.jdespinosa.demo.restaurant.commons.controllers.BasicController;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderRequestDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import com.jdespinosa.demo.restaurant.placeorder.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Order controller implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController extends BasicController<Long, OrderDTO, OrderRequestDTO> implements IOrderController {

    @Autowired
    public OrderController(IOrderService service) {
        super(service);
    }

    @Override
    @GetMapping(path = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderDTO>> findByStatus(@PathVariable("status") OrderStatuses status) {
        List<OrderDTO> orders = ((IOrderService) getService()).findByStatus(status);

        return buildResponse(orders);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderRequestDTO requestBody) {
        OrderDTO order = ((IOrderService) getService()).placeOrder(requestBody);

        return buildResponse(order);
    }

    @Override
    @PatchMapping(path = "/{id}/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> updateStatus(@PathVariable("id") Long id,
                                                 @RequestParam("status") OrderStatuses status) {
        OrderDTO updated = ((IOrderService) getService()).updateStatus(id, status);

        return buildResponse(updated);
    }
}