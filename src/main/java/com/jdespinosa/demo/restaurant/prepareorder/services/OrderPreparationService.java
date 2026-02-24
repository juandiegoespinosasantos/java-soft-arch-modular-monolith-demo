package com.jdespinosa.demo.restaurant.prepareorder.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import com.jdespinosa.demo.restaurant.placeorder.services.IOrderService;
import com.jdespinosa.demo.restaurant.prepareorder.model.dto.OrderPreparationDTO;
import com.jdespinosa.demo.restaurant.prepareorder.model.entities.OrderPreparation;
import com.jdespinosa.demo.restaurant.prepareorder.model.enums.OrderPreparationStatuses;
import com.jdespinosa.demo.restaurant.prepareorder.model.repositories.OrderPreparationRepository;
import com.jdespinosa.demo.restaurant.prepareorder.utils.adapters.OrderPreparationAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Order preparation service implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 23, 2026
 * @since 17
 */
@Service
@Transactional(readOnly = true)
public class OrderPreparationService implements IOrderPreparationService {

    private final OrderPreparationRepository repository;
    private final IOrderService orderService;

    @Autowired
    public OrderPreparationService(OrderPreparationRepository repository, IOrderService orderService) {
        this.repository = repository;
        this.orderService = orderService;
    }

    @Override
    public List<OrderPreparationDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(OrderPreparationAdapter::transform)
                .toList();
    }

    @Override
    public List<OrderPreparationDTO> findByStatus(final OrderPreparationStatuses status) {
        return repository.findByStatus(status)
                .stream()
                .map(OrderPreparationAdapter::transform)
                .toList();
    }

    @Override
    public Optional<OrderPreparationDTO> findByOrderId(final Long orderId) {
        Optional<OrderPreparation> opt = repository.findByOrderId(orderId);
        if (opt.isEmpty()) return Optional.empty();

        OrderPreparationDTO dto = OrderPreparationAdapter.transform(opt.get());

        return Optional.of(dto);
    }

    @Override
    @Transactional
    public OrderPreparationDTO startPreparation(final Long orderId) {
        Optional<OrderDTO> orderOpt = orderService.find(orderId);
        if (orderOpt.isEmpty()) throw new NotFoundException("Order", orderId);

        OrderDTO order = orderOpt.get();

        if (!OrderStatuses.PAID.equals(order.getStatus())) {
            throw new IllegalArgumentException("Only Orders in 'PAID' status can be prepared.");
        }

        Optional<OrderPreparationDTO> pivot = findByOrderId(orderId);
        if (pivot.isPresent()) throw new IllegalArgumentException("A preparation already exists for this order.");

        OrderPreparation orderPreparation = OrderPreparation.builder()
                .orderId(orderId)
                .status(OrderPreparationStatuses.IN_PROGRESS)
                .startedAt(LocalDateTime.now())
                .build();

        repository.save(orderPreparation);
        orderService.updateStatus(orderId, OrderStatuses.PREPARING);

        return OrderPreparationAdapter.transform(orderPreparation);
    }

    @Override
    @Transactional
    public OrderPreparationDTO markReady(final Long orderId, final String chefNotes) {
        Optional<OrderPreparation> pivotOpt = repository.findByOrderId(orderId);
        if (pivotOpt.isEmpty()) throw new NotFoundException("Order preparation", orderId);

        OrderPreparation pivot = pivotOpt.get();
        pivot.setStatus(OrderPreparationStatuses.READY);
        pivot.setChefNotes(chefNotes);
        pivot.setCompletedAt(LocalDateTime.now());

        pivot = repository.save(pivot);
        orderService.updateStatus(orderId, OrderStatuses.READY);

        return OrderPreparationAdapter.transform(pivot);
    }
}