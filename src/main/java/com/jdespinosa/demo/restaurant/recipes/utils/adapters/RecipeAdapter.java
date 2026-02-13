package com.jdespinosa.demo.restaurant.recipes.utils.adapters;

import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import com.jdespinosa.demo.restaurant.recipes.model.entities.Recipe;

import java.util.List;

/**
 * Recipe domain adapter.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
public final class RecipeAdapter {

    private RecipeAdapter() {
    }

    /**
     * Transforms DTO into entity.
     *
     * @param dto Data transfer object.
     * @return Recipe entity.
     */
    public static Recipe transform(final RecipeRequestDTO dto) {
        return Recipe.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .preparationTimeMinutes(dto.preparationTimeMinutes())
                .build();
    }

    /**
     * Transforms entity into DTO.
     *
     * @param entity Entity.
     * @return Recipe data transfer object.
     */
    public static RecipeDTO transform(final Recipe entity) {
        return RecipeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .preparationTimeMinutes(entity.getPreparationTimeMinutes())
                .active(entity.isActive())
                .build();
    }

    /**
     * Transforms a list of entities into a list of DTO.
     *
     * @param entities List of entities.
     * @return List of Recipe data transfer objects.
     */
    public static List<RecipeDTO> transform(final List<Recipe> entities) {
        return entities.stream()
                .map(RecipeAdapter::transform)
                .toList();
    }
}