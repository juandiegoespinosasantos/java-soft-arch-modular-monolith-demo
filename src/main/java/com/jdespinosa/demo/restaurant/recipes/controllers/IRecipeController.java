package com.jdespinosa.demo.restaurant.recipes.controllers;

import com.jdespinosa.demo.restaurant.commons.controllers.IBasicController;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;

/**
 * Recipe controller.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
public interface IRecipeController extends IBasicController<Long, RecipeDTO, RecipeRequestDTO> {
}