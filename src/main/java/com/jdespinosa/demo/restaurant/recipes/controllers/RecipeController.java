package com.jdespinosa.demo.restaurant.recipes.controllers;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.commons.response.ApiResponse;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import com.jdespinosa.demo.restaurant.recipes.services.IRecipeService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Recipe controller implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController implements IRecipeController {

    private final IRecipeService service;

    @Autowired
    public RecipeController(IRecipeService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecipeDTO>> findAll() {
        List<RecipeDTO> list = service.findAll();

        return buildResponse(list);
    }

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> findById(@PathVariable("id") Long id) {
        Optional<RecipeDTO> opt = service.find(id);
        if (opt.isEmpty()) throw new NotFoundException("Recipe", id);

        return buildResponse(opt.get());
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> create(@RequestBody RecipeRequestDTO requestBody) {
        RecipeDTO created = service.create(requestBody);

        return buildResponse(created, HttpStatus.CREATED);
    }

    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> update(@PathVariable("id") Long id,
                                            @RequestBody RecipeRequestDTO requestBody) {
        RecipeDTO updated = service.update(id, requestBody);

        return buildResponse(updated);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        service.delete(id);

        ApiResponse<Void> response = ApiResponse.ok("Recipe.id=" + id + " deleted", null);

        return buildResponse(response);
    }

    private <T> ResponseEntity<T> buildResponse(final T data, final HttpStatusCode statusCode) {
        return new ResponseEntity<>(data, statusCode);
    }

    private <T> ResponseEntity<T> buildResponse(final T data) {
        return buildResponse(data, HttpStatus.OK);
    }
}