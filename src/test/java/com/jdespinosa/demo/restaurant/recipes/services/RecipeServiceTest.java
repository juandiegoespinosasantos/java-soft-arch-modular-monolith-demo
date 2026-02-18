package com.jdespinosa.demo.restaurant.recipes.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import com.jdespinosa.demo.restaurant.recipes.model.entities.Recipe;
import com.jdespinosa.demo.restaurant.recipes.model.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Unit testing for {@link RecipeService}
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository mockRepository;

    private RecipeService service;

    @BeforeEach
    void setUp() {
        service = new RecipeService(mockRepository);
    }

    @Test
    void givenExistentRecipes_whenFindAll_thenReturnListOfDTOs() {
        // given
        List<Recipe> recipes = List.of(
                buildEntity(1L, "Recipe A", 2_000.0, 15, true),
                buildEntity(2L, "Recipe B", 1_500.0, 20, false),
                buildEntity(3L, "Recipe C", 3_800, 30, true));
        Mockito.when(mockRepository.findAll()).thenReturn(recipes);

        // when
        List<RecipeDTO> actualList = service.findAll();

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(recipes.size(), actualList.size());

        Recipe entity;
        RecipeDTO actual;

        for (int i = 0; i < actualList.size(); i++) {
            entity = recipes.get(i);
            actual = actualList.get(i);

            validate(entity, actual);
            Assertions.assertEquals(entity.isActive(), actual.isActive());
        }

        Mockito.verify(mockRepository).findAll();
    }

    @Test
    void givenNoExistentRecipes_whenFindAll_thenReturnEmptyList() {
        // given
        Mockito.when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<RecipeDTO> actualList = service.findAll();

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertTrue(actualList.isEmpty());

        Mockito.verify(mockRepository).findAll();
    }

    @Test
    void givenActiveRecipes_whenFindActives_thenReturnListOfActiveDTOs() {
        // given
        List<Recipe> recipes = List.of(
                buildEntity(1L, "Recipe A", 2_000.0, 15, true),
                buildEntity(2L, "Recipe B", 1_500.0, 20, true),
                buildEntity(3L, "Recipe C", 3_800, 30, true));
        Mockito.when(mockRepository.findByActiveTrue()).thenReturn(recipes);

        // when
        List<RecipeDTO> actualList = service.findActives();

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(recipes.size(), actualList.size());

        Recipe entity;
        RecipeDTO actual;

        for (int i = 0; i < actualList.size(); i++) {
            entity = recipes.get(i);
            actual = actualList.get(i);

            validate(entity, actual);
            Assertions.assertTrue(actual.isActive());
        }

        Mockito.verify(mockRepository).findByActiveTrue();
    }

    @Test
    void givenNoActiveRecipes_whenFindActives_thenReturnEmptyList() {
        // given
        Mockito.when(mockRepository.findByActiveTrue()).thenReturn(Collections.emptyList());

        // when
        List<RecipeDTO> actualList = service.findActives();

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertTrue(actualList.isEmpty());

        Mockito.verify(mockRepository).findByActiveTrue();
    }

    @Test
    void givenExistentId_whenFind_thenReturnOptionalDTO() {
        // given
        long id = 1L;

        Recipe recipe = buildEntity(1L, "Recipe A", 2_000.0, 15, true);
        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.of(recipe));

        // when
        Optional<RecipeDTO> opt = service.find(id);

        // then
        Assertions.assertNotNull(opt);
        Assertions.assertTrue(opt.isPresent());

        RecipeDTO actual = opt.get();

        validate(recipe, actual);
        Assertions.assertEquals(recipe.isActive(), actual.isActive());

        Mockito.verify(mockRepository).findById(id);
    }

    @Test
    void givenNonExistentId_whenFind_thenReturnOptionalEmpty() {
        // given
        long id = 1L;

        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Optional<RecipeDTO> opt = service.find(id);

        // then
        Assertions.assertNotNull(opt);
        Assertions.assertTrue(opt.isEmpty());

        Mockito.verify(mockRepository).findById(id);
    }

    @Test
    void givenRequestBody_whenCreate_thenReturnCreatedDTO() {
        // given
        RecipeRequestDTO requestBody = new RecipeRequestDTO("Recipe A", "This is a test", 2_000.0, 15);
        Recipe toCreate = buildEntity(null, requestBody);
        Recipe created = buildEntity(1L, requestBody);
        Mockito.when(mockRepository.save(toCreate)).thenReturn(created);

        // when
        RecipeDTO actual = service.create(requestBody);

        // then
        validate(created, actual);
        Assertions.assertTrue(actual.isActive());

        Mockito.verify(mockRepository).save(toCreate);
    }

    @Test
    void givenExistentId_whenUpdate_thenReturnUpdatedDTO() {
        // given
        long id = 1L;
        RecipeRequestDTO requestBody = new RecipeRequestDTO("New Recipe A", "This is a new test", 2_100.0, 15);

        Recipe pivot = buildEntity(id, "Recipe A", 2_000.0, 15, true);
        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.of(pivot));

        Recipe updated = buildEntity(id, requestBody);
        Mockito.when(mockRepository.save(pivot)).thenReturn(pivot);

        // when
        RecipeDTO actual = service.update(id, requestBody);

        // then
        validate(updated, actual);
        Assertions.assertTrue(actual.isActive());

        Mockito.verify(mockRepository).save(pivot);
    }

    @Test
    void givenNonExistentId_whenUpdate_thenThrowNotFoundException() {
        // given
        long id = 1L;
        RecipeRequestDTO requestBody = new RecipeRequestDTO("New Recipe A", "This is a new test", 2_100.0, 15);

        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(NotFoundException.class, () -> service.update(id, requestBody), "Entity Recipe.id=1 not found");

        Mockito.verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void givenExistentId_whenDelete_thenSetActiveToFalseAndReturnNothing() {
        // given
        long id = 1L;

        Recipe pivot = buildEntity(id, "Recipe A", 2_000.0, 15, true);
        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.of(pivot));

        Mockito.when(mockRepository.save(pivot)).thenReturn(pivot);

        // when
        service.delete(id);

        // then

        Mockito.verify(mockRepository).save(pivot);
    }

    @Test
    void givenNonExistentId_whenDelete_thenThrowNotFoundException() {
        // given
        long id = 1L;
        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(NotFoundException.class, () -> service.delete(id), "Entity Recipe.id=1 not found");

        Mockito.verifyNoMoreInteractions(mockRepository);
    }

    private Recipe buildEntity(long id, String name, double price, int preparationTimeMinutes, boolean active) {
        return Recipe.builder()
                .id(id)
                .name(name)
                .description(name)
                .price(price)
                .preparationTimeMinutes(preparationTimeMinutes)
                .active(active)
                .build();
    }

    private Recipe buildEntity(Long id, RecipeRequestDTO dto) {
        return Recipe.builder()
                .id(id)
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .preparationTimeMinutes(dto.preparationTimeMinutes())
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
    }
}