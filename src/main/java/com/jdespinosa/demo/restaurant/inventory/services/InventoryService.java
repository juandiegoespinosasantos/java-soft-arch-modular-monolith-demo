package com.jdespinosa.demo.restaurant.inventory.services;

import com.jdespinosa.demo.restaurant.inventory.model.dto.AdjustInventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.entities.Inventory;
import com.jdespinosa.demo.restaurant.inventory.model.repositories.InventoryRepository;
import com.jdespinosa.demo.restaurant.inventory.utils.adapters.InventoryAdapter;
import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Inventory service implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 10, 2026
 * @since 17
 */
@Service
@Transactional(readOnly = true)
public class InventoryService implements IInventoryService {

    private final InventoryRepository repository;

    @Autowired
    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InventoryDTO> findAll() {
        List<Inventory> inventories = repository.findAll();

        return InventoryAdapter.transform(inventories);
    }

    @Override
    public Optional<InventoryDTO> find(final Long id) {
        Optional<Inventory> opt = repository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        InventoryDTO dto = InventoryAdapter.transform(opt.get());

        return Optional.of(dto);
    }

    @Override
    public List<InventoryDTO> findLowStock() {
        List<Inventory> inventories = repository.findLowStock();

        return InventoryAdapter.transform(inventories);
    }

    @Override
    @Transactional
    public InventoryDTO create(final InventoryRequestDTO requestBody) {
        Inventory entity = InventoryAdapter.transform(requestBody);
        Inventory created = repository.save(entity);

        return InventoryAdapter.transform(created);
    }

    @Override
    @Transactional
    public InventoryDTO update(final Long id, final InventoryRequestDTO requestBody) {
        Optional<Inventory> pivotOpt = repository.findById(id);
        if (pivotOpt.isEmpty()) throw new NotFoundException("Inventory", id);

        Inventory pivot = pivotOpt.get();
        pivot.setIngredientName(requestBody.ingredientName());
        pivot.setQuantity(requestBody.quantity());
        pivot.setUnit(requestBody.unit());
        pivot.setMinStock(requestBody.minStock());

        Inventory updated = repository.save(pivot);

        return InventoryAdapter.transform(updated);
    }

    @Override
    @Transactional
    public InventoryDTO adjust(final Long id, final AdjustInventoryRequestDTO requestBody) {
        Optional<Inventory> pivotOpt = repository.findById(id);
        if (pivotOpt.isEmpty()) throw new NotFoundException("Inventory", id);

        Inventory pivot = pivotOpt.get();
        double newQty = pivot.getQuantity() + requestBody.quantity();
        if (newQty < 0.0) throw new IllegalArgumentException("Insufficient stock for the adjustment");

        pivot.setQuantity(newQty);
        Inventory updated = repository.save(pivot);

        return InventoryAdapter.transform(updated);
    }
}