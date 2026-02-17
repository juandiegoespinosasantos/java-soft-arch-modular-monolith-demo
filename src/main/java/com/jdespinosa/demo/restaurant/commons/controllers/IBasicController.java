package com.jdespinosa.demo.restaurant.commons.controllers;

import com.jdespinosa.demo.restaurant.commons.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Basic controller interface.
 *
 * @param <I> Type of ID.
 * @param <T> Type of DTO.
 * @param <R> Type of request body DTO.
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 16, 2026
 * @since 17
 */
public interface IBasicController<I, T, R> {

    ResponseEntity<List<T>> findAll();

    ResponseEntity<T> findById(I id);

    ResponseEntity<T> create(R requestBody);

    ResponseEntity<T> update(I id, R requestBody);

    ResponseEntity<ApiResponse<Void>> delete(I id);
}