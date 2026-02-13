package com.jdespinosa.demo.restaurant.recipes.model.repositories;

import com.jdespinosa.demo.restaurant.recipes.model.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Recipe repository.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 12, 2026
 * @since 17
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByActiveTrue();
}