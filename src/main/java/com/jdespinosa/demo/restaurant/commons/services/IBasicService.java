package com.jdespinosa.demo.restaurant.commons.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Basic service interface.
 *
 * @param <I> Type of ID.
 * @param <T> Type of DTO.
 * @param <R> Type of request body DTO.
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 16, 2026
 * @since 17
 */
public interface IBasicService<I, T, R> {

    List<T> findAll();

    Optional<T> find(I id);

    T create(R requestBody);

    T update(I id, R requestBody) throws NotFoundException;

    void delete(I id) throws NotFoundException;
}