package com.jdespinosa.demo.restaurant.recipes.utils.adapters;

import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import com.jdespinosa.demo.restaurant.recipes.model.entities.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit testing for {@link RecipeAdapter}
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
class RecipeAdapterTest {

    @Test
    void givenDTO_whenTransform_thenReturnEntity() {
        // given
        RecipeRequestDTO dto = new RecipeRequestDTO("Test name", "Test description", 2_000.0, 15);

        // when
        Recipe actual = RecipeAdapter.transform(dto);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(dto.name(), actual.getName());
        Assertions.assertEquals(dto.description(), actual.getDescription());
        Assertions.assertEquals(dto.price(), actual.getPrice());
        Assertions.assertEquals(dto.preparationTimeMinutes(), actual.getPreparationTimeMinutes());
    }

    @Test
    void givenEntity_whenTransform_thenReturnDTO() {
        // given
        Recipe entity = buildEntity();

        // when
        RecipeDTO actual = RecipeAdapter.transform(entity);

        // then
        validate(entity, actual);
    }

    @Test
    void givenListOfEntities_whenTransform_thenReturnListOfDTOs() {
        // given
        List<Recipe> entities = List.of(buildEntity());

        // when
        List<RecipeDTO> actualList = RecipeAdapter.transform(entities);

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertFalse(actualList.isEmpty());
        Assertions.assertEquals(entities.size(), actualList.size());

        Recipe entity = entities.get(0);
        RecipeDTO actual = actualList.get(0);
        validate(entity, actual);
    }

    private Recipe buildEntity() {
        return Recipe.builder()
                .id(33L)
                .name("Test name")
                .description("Test description")
                .price(2_000.0)
                .preparationTimeMinutes(15)
                .active(true)
                .build();
    }

    private void validate(Recipe expected, RecipeDTO actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(expected.getPreparationTimeMinutes(), actual.getPreparationTimeMinutes());
        Assertions.assertEquals(expected.isActive(), actual.isActive());
    }
}