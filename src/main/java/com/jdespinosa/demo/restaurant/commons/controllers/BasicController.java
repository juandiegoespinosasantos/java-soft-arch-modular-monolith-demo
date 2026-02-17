package com.jdespinosa.demo.restaurant.commons.controllers;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.commons.response.ApiResponse;
import com.jdespinosa.demo.restaurant.commons.services.IBasicService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/**
 * Basic controller abstract class.
 *
 * @param <I> Type of ID.
 * @param <T> Type of DTO.
 * @param <R> Type of request body DTO.
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 16, 2026
 * @since 17
 */
public abstract class BasicController<I, T, R> implements IBasicController<I, T, R> {

    @Getter
    private final IBasicService<I, T, R> service;

    @Autowired
    protected BasicController(IBasicService<I, T, R> service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<T>> findAll() {
        List<T> list = service.findAll();

        return buildResponse(list);
    }

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> findById(@PathVariable("id") I id) {
        Optional<T> opt = service.find(id);
        if (opt.isEmpty()) throw new NotFoundException("Inventory", id.toString());

        return buildResponse(opt.get());
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> create(@RequestBody R requestBody) {
        T created = service.create(requestBody);

        return buildResponse(created, HttpStatus.CREATED);
    }

    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> update(@PathVariable("id") I id,
                                    @RequestBody R requestBody) {
        T updated = service.update(id, requestBody);

        return buildResponse(updated);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") I id) {
        service.delete(id);

        ApiResponse<Void> response = ApiResponse.ok("Recipe.id=" + id + " deleted", null);

        return buildResponse(response);
    }

    protected <T> ResponseEntity<T> buildResponse(final T data, final HttpStatusCode statusCode) {
        return new ResponseEntity<>(data, statusCode);
    }

    protected <T> ResponseEntity<T> buildResponse(final T data) {
        return buildResponse(data, HttpStatus.OK);
    }
}