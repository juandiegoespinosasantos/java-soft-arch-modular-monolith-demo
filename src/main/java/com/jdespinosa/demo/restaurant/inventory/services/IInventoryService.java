package com.jdespinosa.demo.restaurant.inventory.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.commons.services.IBasicService;
import com.jdespinosa.demo.restaurant.inventory.model.dto.AdjustInventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;

import java.util.List;

/**
 * Inventory service.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 10, 2026
 * @since 17
 */
public interface IInventoryService extends IBasicService<Long, InventoryDTO, InventoryRequestDTO> {

    List<InventoryDTO> findLowStock();

    InventoryDTO adjust(Long id, AdjustInventoryRequestDTO requestBody) throws NotFoundException;
}