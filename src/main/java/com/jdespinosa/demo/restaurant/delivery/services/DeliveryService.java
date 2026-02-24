package com.jdespinosa.demo.restaurant.delivery.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.delivery.model.dto.DeliveryDTO;
import com.jdespinosa.demo.restaurant.delivery.model.dto.DeliveryRequestDTO;
import com.jdespinosa.demo.restaurant.delivery.model.entities.Delivery;
import com.jdespinosa.demo.restaurant.delivery.model.enums.DeliveryStatuses;
import com.jdespinosa.demo.restaurant.delivery.model.repositories.DeliveryRepository;
import com.jdespinosa.demo.restaurant.delivery.utils.adapters.DeliveryAdapter;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import com.jdespinosa.demo.restaurant.placeorder.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Delivery service.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@Service
@Transactional(readOnly = true)
public class DeliveryService implements IDeliveryService {

    private final DeliveryRepository repository;
    private final IOrderService orderService;

    @Autowired
    public DeliveryService(DeliveryRepository repository, IOrderService orderService) {
        this.repository = repository;
        this.orderService = orderService;
    }

    @Override
    public List<DeliveryDTO> findAll() {
        List<Delivery> deliveries = repository.findAll();

        return DeliveryAdapter.transform(deliveries);
    }

    @Override
    public List<DeliveryDTO> findPending() {
        List<Delivery> deliveries = repository.findByStatus(DeliveryStatuses.PENDING);

        return DeliveryAdapter.transform(deliveries);
    }

    @Override
    @Transactional
    public DeliveryDTO deliver(final DeliveryRequestDTO requestBody) {
        Long reqOrderId = requestBody.orderId();

        Optional<OrderDTO> orderOpt = orderService.find(reqOrderId);
        if (orderOpt.isEmpty()) throw new NotFoundException("Order", reqOrderId);

        OrderDTO order = orderOpt.get();

        if (!OrderStatuses.READY.equals(order.getStatus())) {
            throw new IllegalArgumentException("Order must be in 'READY' status to be delivered.");
        }

        final Long orderId = order.getId();

        Optional<Delivery> deliveryOpt = repository.findByOrderId(orderId);
        if (deliveryOpt.isPresent()) throw new IllegalArgumentException("The Order has been delivered already.");

        Delivery delivery = Delivery.builder()
                .orderId(orderId)
                .waiterName(requestBody.waiterName())
                .status(DeliveryStatuses.DELIVERED)
                .deliveredAt(LocalDateTime.now())
                .build();
        delivery = repository.save(delivery);

        orderService.updateStatus(orderId, OrderStatuses.DELIVERED);

        return DeliveryAdapter.transform(delivery);
    }
}