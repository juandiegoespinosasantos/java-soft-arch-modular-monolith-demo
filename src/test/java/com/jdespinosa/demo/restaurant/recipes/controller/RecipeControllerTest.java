package com.jdespinosa.demo.restaurant.recipes.controller;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import com.jdespinosa.demo.restaurant.commons.response.ApiResponse;
import com.jdespinosa.demo.restaurant.recipes.controllers.RecipeController;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeDTO;
import com.jdespinosa.demo.restaurant.recipes.model.dto.RecipeRequestDTO;
import com.jdespinosa.demo.restaurant.recipes.services.IRecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Unit testing for {@link RecipeController}
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private IRecipeService mockService;

    private RecipeController controller;

    @BeforeEach
    void setUp() {
        controller = new RecipeController(mockService);
    }

    @Test
    void givenExistentRecipes_whenFindAll_thenReturnResponseEntityOK() {
        // given
        List<RecipeDTO> recipes = List.of(
                buildDTO(1L, "Recipe A", 2_000.0, 15),
                buildDTO(2L, "Recipe B", 1_500.0, 20),
                buildDTO(3L, "Recipe C", 3_800, 30));
        Mockito.when(mockService.findAll()).thenReturn(recipes);

        // when
        ResponseEntity<List<RecipeDTO>> actualResponse = controller.findAll();

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        List<RecipeDTO> actualList = actualResponse.getBody();
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(recipes.size(), actualList.size());

        RecipeDTO entity;
        RecipeDTO actual;

        for (int i = 0; i < actualList.size(); i++) {
            entity = recipes.get(i);
            actual = actualList.get(i);

            validate(entity, actual);
        }

        Mockito.verify(mockService).findAll();
    }

    @Test
    void givenNoExistentRecipes_whenFindAll_thenReturnResponseEntityNotFound() {
        // given
        Mockito.when(mockService.findAll()).thenReturn(Collections.emptyList());

        // when
        ResponseEntity<List<RecipeDTO>> actualResponse = controller.findAll();

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        Mockito.verify(mockService).findAll();
    }

    @Test
    void givenExistentId_whenFind_thenReturnResponseEntityOK() {
        // given
        long id = 1L;

        RecipeDTO recipe = buildDTO(1L, "Recipe A", 2_000.0, 15);
        Mockito.when(mockService.find(id)).thenReturn(Optional.of(recipe));

        // when
        ResponseEntity<RecipeDTO> actualResponse = controller.findById(id);

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        RecipeDTO actual = actualResponse.getBody();
        validate(recipe, actual);

        Mockito.verify(mockService).find(id);
    }

    @Test
    void givenNonExistentId_whenFind_thenReturnResponseEntityNotFound() {
        // given
        long id = 1L;

        Mockito.when(mockService.find(id)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(NotFoundException.class, () -> controller.findById(id), "Entity Recipe.id=1 not found");

        Mockito.verify(mockService).find(id);
    }

    @Test
    void givenRequestBody_whenCreate_thenReturnResponseEntityCreated() {
        // given
        RecipeRequestDTO requestBody = new RecipeRequestDTO("Recipe A", "This is a test", 2_000.0, 15);

        RecipeDTO created = buildDTO(1L, requestBody);
        Mockito.when(mockService.create(requestBody)).thenReturn(created);

        // when
        ResponseEntity<RecipeDTO> actualResponse = controller.create(requestBody);

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());

        RecipeDTO actual = actualResponse.getBody();
        validate(created, actual);

        Mockito.verify(mockService).create(requestBody);
    }

    @Test
    void givenRequestBody_whenUpdate_thenReturnResponseEntityOK() {
        // given
        long id = 1L;
        RecipeRequestDTO requestBody = new RecipeRequestDTO("Recipe A", "This is a test", 2_000.0, 15);

        RecipeDTO updated = buildDTO(id, requestBody);
        Mockito.when(mockService.update(id, requestBody)).thenReturn(updated);

        // when
        ResponseEntity<RecipeDTO> actualResponse = controller.update(id, requestBody);

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        RecipeDTO actual = actualResponse.getBody();
        validate(updated, actual);

        Mockito.verify(mockService).update(id, requestBody);
    }

    @Test
    void givenId_whenDelete_thenReturnResponseEntityOK() {
        // given
        long id = 1L;

        Mockito.doNothing().when(mockService).delete(id);

        // when
        ResponseEntity<ApiResponse<Void>> actualResponse = controller.delete(id);

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals("Recipe.id=1 deleted", actualResponse.getBody().getMessage());

        Mockito.verify(mockService).delete(id);
    }

    private RecipeDTO buildDTO(long id, RecipeRequestDTO requestBody) {
        return RecipeDTO.builder()
                .id(id)
                .name(requestBody.name())
                .description(requestBody.description())
                .price(requestBody.price())
                .preparationTimeMinutes(requestBody.preparationTimeMinutes())
                .active(true)
                .build();
    }

    private RecipeDTO buildDTO(long id, String name, double price, int preparationTimeMinutes) {
        return RecipeDTO.builder()
                .id(id)
                .name(name)
                .description(name)
                .price(price)
                .preparationTimeMinutes(preparationTimeMinutes)
                .active(true)
                .build();
    }

    private void validate(RecipeDTO expected, RecipeDTO actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(expected.getPreparationTimeMinutes(), actual.getPreparationTimeMinutes());
        Assertions.assertEquals(expected.isActive(), actual.isActive());
    }
}