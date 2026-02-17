package com.jdespinosa.demo.restaurant.inventory.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.commons.services.BasicService;
import com.jdespinosa.demo.restaurant.inventory.model.dto.AdjustInventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.entities.Inventory;
import com.jdespinosa.demo.restaurant.inventory.model.repositories.InventoryRepository;
import com.jdespinosa.demo.restaurant.inventory.utils.adapters.InventoryAdapter;
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
public class InventoryService extends BasicService<Long, Inventory, InventoryDTO, InventoryRequestDTO> implements IInventoryService {

    @Autowired
    public InventoryService(InventoryRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Inventory";
    }

    @Override
    protected List<InventoryDTO> transformToTOList(final List<Inventory> entities) {
        return InventoryAdapter.transform(entities);
    }

    @Override
    protected InventoryDTO transformToTO(final Inventory entity) {
        return InventoryAdapter.transform(entity);
    }

    @Override
    protected Inventory transformToEntity(final InventoryRequestDTO requestBody) {
        return InventoryAdapter.transform(requestBody);
    }

    @Override
    protected void updateEntity(final Inventory pivot, final InventoryRequestDTO requestBody) {
        pivot.setIngredientName(requestBody.ingredientName());
        pivot.setQuantity(requestBody.quantity());
        pivot.setUnit(requestBody.unit());
        pivot.setMinStock(requestBody.minStock());
    }

    @Override
    public List<InventoryDTO> findLowStock() {
        List<Inventory> inventories = ((InventoryRepository) getRepository()).findLowStock();

        return transformToTOList(inventories);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        throw new UnsupportedOperationException("Delete Inventory not supported just yet!");
    }

    @Override
    @Transactional
    public InventoryDTO adjust(final Long id, final AdjustInventoryRequestDTO requestBody) throws NotFoundException {
        Optional<Inventory> pivotOpt = getRepository().findById(id);
        if (pivotOpt.isEmpty()) throw new NotFoundException(getEntityName(), id);

        Inventory pivot = pivotOpt.get();
        double newQty = pivot.getQuantity() + requestBody.quantity();
        if (newQty < 0.0) throw new IllegalArgumentException("Insufficient stock for the adjustment");

        pivot.setQuantity(newQty);
        Inventory adjusted = getRepository().save(pivot);

        return transformToTO(adjusted);
    }
}