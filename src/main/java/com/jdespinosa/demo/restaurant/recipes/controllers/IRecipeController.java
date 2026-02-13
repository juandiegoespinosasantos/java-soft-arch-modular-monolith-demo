package com.jdespinosa.demo.restaurant.recipes.controllers;

import com.jdespinosa.demo.restaurant.commons.response.ApiResponse;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
public interface IRecipeController {

    ResponseEntity<List<RecipeDTO>> findAll();

    ResponseEntity<RecipeDTO> findById(Long id);

    ResponseEntity<RecipeDTO> create(RecipeRequestDTO requestBody);

    ResponseEntity<RecipeDTO> update(Long id, RecipeRequestDTO requestBody);

    ResponseEntity<ApiResponse<Void>> delete(Long id);
}