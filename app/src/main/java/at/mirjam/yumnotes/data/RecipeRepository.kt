package at.mirjam.yumnotes.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RecipeRepository(private val recipeDao: RecipeDao) {
    // READ
    fun getAllRecipes(): Flow<List<Recipe>> {
        return try {
            Log.d("RecipeRepository", "Fetching recipes from database.")
            recipeDao.getAllRecipes()
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error fetching recipes: ${e.message}")
            flowOf(emptyList())
        }
    }
    // CREATE
    suspend fun insertRecipe(recipe: Recipe) {
        try {
            recipeDao.insertRecipe(recipe)
            Log.d("RecipeRepository", "Recipe inserted successfully: $recipe")
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error inserting recipe: ${e.message}")
        }
    }
    // DELETE
    suspend fun deleteRecipe(recipe: Recipe) {
        try {
            recipeDao.deleteRecipe(recipe)
            Log.d("RecipeRepository", "Recipe deleted successfully: $recipe")
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error deleting recipe: ${e.message}")
        }
    }
    // UPDATE
    suspend fun updateRecipe(recipe: Recipe) {
        try {
            recipeDao.updateRecipe(recipe)
            Log.d("RecipeRepository", "Recipe updated successfully: $recipe")
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error updating recipe: ${e.message}")
        }
    }
}