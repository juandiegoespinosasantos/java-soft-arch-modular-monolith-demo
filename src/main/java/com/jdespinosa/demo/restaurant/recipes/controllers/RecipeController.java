package com.jdespinosa.demo.restaurant.recipes.controllers;

import com.jdespinosa.demo.restaurant.commons.controllers.BasicController;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import com.jdespinosa.demo.restaurant.recipes.services.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Recipe controller implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController extends BasicController<Long, RecipeDTO, RecipeRequestDTO> implements IRecipeController {

    @Autowired
    public RecipeController(IRecipeService service) {
        super(service);
    }
}