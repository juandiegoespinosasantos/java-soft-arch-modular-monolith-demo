package com.jdespinosa.demo.restaurant.inventory.controller;

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
public interface IInventoryController {

    ResponseEntity<List<InventoryDTO>> findAll();

    ResponseEntity<InventoryDTO> findById(Long id);

    ResponseEntity<List<InventoryDTO>> lowStock();

    ResponseEntity<InventoryDTO> create(InventoryRequestDTO requestBody);

    ResponseEntity<InventoryDTO> update(Long id, InventoryRequestDTO requestBody);

    ResponseEntity<InventoryDTO> adjust(Long id, AdjustInventoryRequestDTO requestBody);
}