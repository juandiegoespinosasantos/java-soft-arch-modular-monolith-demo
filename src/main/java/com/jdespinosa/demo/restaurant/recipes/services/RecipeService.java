package com.jdespinosa.demo.restaurant.recipes.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import com.jdespinosa.demo.restaurant.recipes.model.entities.Recipe;
import com.jdespinosa.demo.restaurant.recipes.model.repositories.RecipeRepository;
import com.jdespinosa.demo.restaurant.recipes.utils.adapters.RecipeAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Recipe service implementation.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
@Service
@Transactional(readOnly = true)
public class RecipeService implements IRecipeService {

    private final RecipeRepository repository;

    @Autowired
    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RecipeDTO> findAll() {
        List<Recipe> recipes = repository.findAll();

        return RecipeAdapter.transform(recipes);
    }

    @Override
    public List<RecipeDTO> findActives() {
        List<Recipe> recipes = repository.findByActiveTrue();

        return RecipeAdapter.transform(recipes);
    }

    @Override
    public Optional<RecipeDTO> find(final Long id) throws NotFoundException {
        Optional<Recipe> opt = repository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        RecipeDTO dto = RecipeAdapter.transform(opt.get());

        return Optional.of(dto);
    }

    @Override
    @Transactional
    public RecipeDTO create(final RecipeRequestDTO requestBody) {
        Recipe entity = RecipeAdapter.transform(requestBody);
        entity.setActive(true);

        entity = repository.save(entity);

        return RecipeAdapter.transform(entity);
    }

    @Override
    @Transactional
    public RecipeDTO update(final Long id, final RecipeRequestDTO requestBody) throws NotFoundException {
        Optional<Recipe> pivotOpt = repository.findById(id);
        if (pivotOpt.isEmpty()) throw new NotFoundException("Recipe", id);

        Recipe pivot = pivotOpt.get();
        pivot.setName(requestBody.name());
        pivot.setDescription(requestBody.description());
        pivot.setPrice(requestBody.price());
        pivot.setPreparationTimeMinutes(requestBody.preparationTimeMinutes());

        pivot = repository.save(pivot);

        return RecipeAdapter.transform(pivot);
    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        Optional<Recipe> pivotOpt = repository.findById(id);
        if (pivotOpt.isEmpty()) throw new NotFoundException("Recipe", id);

        Recipe pivot = pivotOpt.get();
        pivot.setActive(false);

        repository.save(pivot);
    }

    // For internal cross-module use
    // TODO
    @Override
    public Recipe getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe", id));
    }
}