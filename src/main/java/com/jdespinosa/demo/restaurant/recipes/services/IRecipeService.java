package com.jdespinosa.demo.restaurant.recipes.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import com.jdespinosa.demo.restaurant.recipes.model.entities.Recipe;

import java.util.List;
import java.util.Optional;

/**
 * Recipe service.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
public interface IRecipeService {

    List<RecipeDTO> findAll();

    List<RecipeDTO> findActives();

    Optional<RecipeDTO> find(Long id) throws NotFoundException;

    RecipeDTO create(RecipeRequestDTO requestBody);

    RecipeDTO update(Long id, RecipeRequestDTO requestBody) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    Recipe getEntity(Long id);
}