package com.jdespinosa.demo.restaurant.placeorder.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.commons.services.BasicService;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderItemRequestDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.dto.OrderRequestDTO;
import com.jdespinosa.demo.restaurant.placeorder.model.entities.Order;
import com.jdespinosa.demo.restaurant.placeorder.model.entities.OrderItem;
import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import com.jdespinosa.demo.restaurant.placeorder.model.repositories.OrderRepository;
import com.jdespinosa.demo.restaurant.placeorder.utils.adapters.OrderAdapter;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.services.IRecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Order service implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends BasicService<Long, Order, OrderDTO, OrderRequestDTO> implements IOrderService {

    private final IRecipeService recipeService;

    public OrderService(OrderRepository repository, IRecipeService recipeService) {
        super(repository);
        this.recipeService = recipeService;
    }

    @Override
    protected String getEntityName() {
        return "Order";
    }

    @Override
    protected List<OrderDTO> transformToDTOList(final List<Order> entities) {
        return OrderAdapter.transform(entities);
    }

    @Override
    protected OrderDTO transformToDTO(final Order entity) {
        return OrderAdapter.transform(entity);
    }

    @Override
    protected Order transformToEntity(final OrderRequestDTO requestBody) {
        return OrderAdapter.transform(requestBody);
    }

    @Override
    protected void updateEntity(final Order pivot, final OrderRequestDTO requestBody) {
        // N/A
    }

    @Override
    public List<OrderDTO> findAll() {
        List<Order> orders = ((OrderRepository) getRepository()).findAllByOrderByCreatedAtDesc();

        return transformToDTOList(orders);
    }

    @Override
    public List<OrderDTO> findByStatus(final OrderStatuses status) {
        List<Order> orders = ((OrderRepository) getRepository()).findByStatus(status);

        return transformToDTOList(orders);
    }

    @Override
    @Transactional
    public OrderDTO create(final OrderRequestDTO requestBody) {
        return placeOrder(requestBody);
    }

    @Override
    @Transactional
    public OrderDTO placeOrder(final OrderRequestDTO requestBody) {
        Order order = transformToEntity(requestBody);

        double total = 0.0;
        OrderItem item;

        for (OrderItemRequestDTO reqItem : requestBody.items()) {
            item = processItem(reqItem, order);
            order.addItem(item);

            total += item.getSubtotal();
        }

        order.setTotal(total);
        order.setStatus(OrderStatuses.PENDING);

        order = getRepository().save(order);

        return transformToDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO updateStatus(final Long id, final OrderStatuses newStatus) {
        Optional<Order> pivotOpt = getRepository().findById(id);
        if (pivotOpt.isEmpty()) throw new NotFoundException(getEntityName(), id);

        Order pivot = pivotOpt.get();
        pivot.setStatus(newStatus);

        pivot = getRepository().save(pivot);

        return transformToDTO(pivot);
    }

    private OrderItem processItem(final OrderItemRequestDTO reqItem, final Order order) throws IllegalArgumentException {
        Long reqRecipeId = reqItem.recipeId();
        Optional<RecipeDTO> recipeOpt = recipeService.find(reqRecipeId);
        if (recipeOpt.isEmpty()) throw new NotFoundException("Recipe", reqRecipeId);

        RecipeDTO recipe = recipeOpt.get();
        String recipeName = recipe.getName();
        if (!recipe.isActive()) throw new IllegalArgumentException("Recipe not available: " + recipeName);

        double recipePrice = recipe.getPrice();
        int itemQty = reqItem.quantity();
        double subtotal = recipePrice * itemQty;

        return OrderItem.builder()
                .order(order)
                .recipeId(recipe.getId())
                .recipeName(recipeName)
                .quantity(itemQty)
                .unitPrice(recipePrice)
                .subtotal(subtotal)
                .build();
    }
}