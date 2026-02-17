package com.jdespinosa.demo.restaurant.recipes.services;

import com.jdespinosa.demo.restaurant.commons.services.IBasicService;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import com.jdespinosa.demo.restaurant.recipes.model.entities.Recipe;

import java.util.List;

/**
 * Recipe service.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
public interface IRecipeService extends IBasicService<Long, RecipeDTO, RecipeRequestDTO> {

    List<RecipeDTO> findActives();

    Recipe getEntity(Long id);
}