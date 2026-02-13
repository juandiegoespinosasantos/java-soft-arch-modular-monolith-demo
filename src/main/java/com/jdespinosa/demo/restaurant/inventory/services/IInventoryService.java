package com.jdespinosa.demo.restaurant.inventory.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.inventory.model.dto.AdjustInventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;

import java.util.List;
import java.util.Optional;

/**
 * Inventory service.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 10, 2026
 * @since 17
 */
public interface IInventoryService {

    List<InventoryDTO> findAll();

    List<InventoryDTO> findLowStock();

    Optional<InventoryDTO> find(Long id);

    InventoryDTO create(InventoryRequestDTO requestBody);

    InventoryDTO update(Long id, InventoryRequestDTO requestBody) throws NotFoundException;

    InventoryDTO adjust(Long id, AdjustInventoryRequestDTO requestBody) throws NotFoundException;
}