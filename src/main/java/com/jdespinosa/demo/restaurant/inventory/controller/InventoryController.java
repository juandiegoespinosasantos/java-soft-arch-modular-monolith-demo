package com.jdespinosa.demo.restaurant.inventory.controller;

import com.jdespinosa.demo.restaurant.inventory.model.dto.AdjustInventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryDTO;
import com.jdespinosa.demo.restaurant.inventory.model.dto.InventoryRequestDTO;
import com.jdespinosa.demo.restaurant.inventory.services.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Inventory controller implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 11, 2026
 * @since 17
 */
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController implements IInventoryController {

    private final IInventoryService service;

    @Autowired
    public InventoryController(IInventoryService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> findAll() {
        List<InventoryDTO> list = service.findAll();
        HttpStatus status = list.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;

        return buildResponse(list, status);
    }

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryDTO> findById(@PathVariable("id") Long id) {
        Optional<InventoryDTO> opt = service.find(id);
        HttpStatus status = opt.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return buildResponse(opt.orElse(null), status);
    }

    @Override
    @GetMapping(path = "/low-stock")
    public ResponseEntity<List<InventoryDTO>> lowStock() {
        List<InventoryDTO> list = service.findLowStock();
        HttpStatus status = list.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;

        return buildResponse(list, status);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryDTO> create(@RequestBody InventoryRequestDTO requestBody) {
        InventoryDTO created = service.create(requestBody);

        return buildResponse(created, HttpStatus.CREATED);
    }

    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryDTO> update(@PathVariable("id") Long id,
                                               @RequestBody InventoryRequestDTO requestBody) {
        InventoryDTO updated = service.update(id, requestBody);

        return buildResponse(updated, HttpStatus.OK);
    }

    @Override
    @PatchMapping(path = "/{id}/adjust", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryDTO> adjust(@PathVariable("id") Long id,
                                               @RequestBody AdjustInventoryRequestDTO requestBody) {
        InventoryDTO updated = service.adjust(id, requestBody);

        return buildResponse(updated, HttpStatus.OK);
    }

    private <T> ResponseEntity<T> buildResponse(final T data, final HttpStatusCode statusCode) {
        return new ResponseEntity<>(data, statusCode);
    }
}