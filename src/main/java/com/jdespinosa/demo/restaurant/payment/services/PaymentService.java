package com.jdespinosa.demo.restaurant.payment.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.payment.model.dto.PaymentDTO;
import com.jdespinosa.demo.restaurant.payment.model.dto.PaymentRequestDTO;
import com.jdespinosa.demo.restaurant.payment.model.entities.Payment;
import com.jdespinosa.demo.restaurant.payment.model.enums.PaymentStatuses;
import com.jdespinosa.demo.restaurant.payment.model.repositories.PaymentRepository;
import com.jdespinosa.demo.restaurant.payment.utils.adapters.PaymentAdapter;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import com.jdespinosa.demo.restaurant.placeorder.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PaymentService implements IPaymentService {

    private final PaymentRepository repository;
    private final OrderService orderService;

    @Autowired
    public PaymentService(PaymentRepository repository, OrderService orderService) {
        this.repository = repository;
        this.orderService = orderService;
    }

    @Override
    public List<PaymentDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(PaymentAdapter::transform)
                .toList();
    }

    @Override
    public Optional<PaymentDTO> findByOrderId(final Long orderId) {
        Optional<Payment> opt = repository.findByOrderId(orderId);
        if (opt.isEmpty()) return Optional.empty();

        PaymentDTO dto = PaymentAdapter.transform(opt.get());

        return Optional.of(dto);
    }

    @Override
    @Transactional
    public PaymentDTO processPayment(final PaymentRequestDTO requestBody) {
        Long reqOrderId = requestBody.orderId();

        Optional<OrderDTO> orderOpt = orderService.find(reqOrderId);
        if (orderOpt.isEmpty()) throw new NotFoundException("Order", reqOrderId);

        OrderDTO order = orderOpt.get();

        if (!OrderStatuses.PENDING.equals(order.getStatus())) {
            throw new IllegalArgumentException("Only orders in 'PENDING' status can be paid.");
        }

        Optional<PaymentDTO> pivot = findByOrderId(reqOrderId);
        if (pivot.isPresent()) throw new IllegalArgumentException("The order has already a payment registered.");

        final Long orderId = order.getId();

        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(order.getTotal())
                .method(requestBody.method())
                .status(PaymentStatuses.COMPLETED)
                .paidAt(LocalDateTime.now())
                .build();
        payment = repository.save(payment);

        orderService.updateStatus(orderId, OrderStatuses.PAID);

        return PaymentAdapter.transform(payment);
    }
}