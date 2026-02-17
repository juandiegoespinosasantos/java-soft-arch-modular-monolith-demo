package com.jdespinosa.demo.restaurant.recipes.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.commons.services.BasicService;
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
public class RecipeService extends BasicService<Long, Recipe, RecipeDTO, RecipeRequestDTO> implements IRecipeService {

    @Autowired
    public RecipeService(RecipeRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Recipe";
    }

    @Override
    protected List<RecipeDTO> transformToTOList(final List<Recipe> entities) {
        return RecipeAdapter.transform(entities);
    }

    @Override
    protected RecipeDTO transformToTO(final Recipe entity) {
        return RecipeAdapter.transform(entity);
    }

    @Override
    protected Recipe transformToEntity(final RecipeRequestDTO requestBody) {
        return RecipeAdapter.transform(requestBody);
    }

    @Override
    protected void updateEntity(final Recipe pivot, final RecipeRequestDTO requestBody) {
        pivot.setName(requestBody.name());
        pivot.setDescription(requestBody.description());
        pivot.setPrice(requestBody.price());
        pivot.setPreparationTimeMinutes(requestBody.preparationTimeMinutes());
    }

    @Override
    public List<RecipeDTO> findActives() {
        List<Recipe> recipes = ((RecipeRepository) getRepository()).findByActiveTrue();

        return transformToTOList(recipes);
    }

    @Override
    public RecipeDTO create(RecipeRequestDTO requestBody) {
        Recipe entity = transformToEntity(requestBody);
        entity.setActive(true);
        entity = getRepository().save(entity);

        return transformToTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        Optional<Recipe> pivotOpt = getRepository().findById(id);
        if (pivotOpt.isEmpty()) throw new NotFoundException("Recipe", id);

        Recipe pivot = pivotOpt.get();
        pivot.setActive(false);

        getRepository().save(pivot);
    }

    // For internal cross-module use
    // TODO
    @Override
    public Recipe getEntity(Long id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe", id));
    }
}