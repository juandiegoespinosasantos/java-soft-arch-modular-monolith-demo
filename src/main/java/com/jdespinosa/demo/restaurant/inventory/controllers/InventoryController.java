package com.jdespinosa.demo.restaurant.inventory.controllers;

import com.jdespinosa.demo.restaurant.commons.controllers.BasicController;
import com.jdespinosa.demo.restaurant.inventory.model.dto.AdjustInventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.services.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Inventory controller implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 11, 2026
 * @since 17
 */
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController extends BasicController<Long, InventoryDTO, InventoryRequestDTO> implements IInventoryController {

    @Autowired
    public InventoryController(IInventoryService service) {
        super(service);
    }

    @Override
    @GetMapping(path = "/low-stock")
    public ResponseEntity<List<InventoryDTO>> lowStock() {
        List<InventoryDTO> list = ((IInventoryService) getService()).findLowStock();

        return buildResponse(list);
    }

    @Override
    @PatchMapping(path = "/{id}/adjust", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryDTO> adjust(@PathVariable("id") Long id,
                                               @RequestBody AdjustInventoryRequestDTO requestBody) {
        InventoryDTO adjusted = ((IInventoryService) getService()).adjust(id, requestBody);

        return buildResponse(adjusted);
    }
}