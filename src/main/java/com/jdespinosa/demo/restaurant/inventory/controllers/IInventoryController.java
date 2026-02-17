package com.jdespinosa.demo.restaurant.inventory.controllers;

import com.jdespinosa.demo.restaurant.commons.controllers.IBasicController;
import com.jdespinosa.demo.restaurant.inventory.model.dto.AdjustInventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Inventory controller.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 11, 2026
 * @since 17
 */
public interface IInventoryController extends IBasicController<Long, InventoryDTO, InventoryRequestDTO> {

    ResponseEntity<List<InventoryDTO>> lowStock();

    ResponseEntity<InventoryDTO> adjust(Long id, AdjustInventoryRequestDTO requestBody);
}