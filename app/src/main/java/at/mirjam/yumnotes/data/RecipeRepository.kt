package at.mirjam.yumnotes.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RecipeRepository(private val recipeDao: RecipeDao) {

    fun getAllRecipes(): Flow<List<Recipe>> {
        return try {
            Log.d("RecipeRepository", "Fetching recipes from database.")
            recipeDao.getAllRecipes()
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error fetching recipes: ${e.message}")
            flowOf(emptyList())
        }
    }

    suspend fun insertRecipe(recipe: Recipe) {
        try {
            recipeDao.insertRecipe(recipe) // Insert recipe into the database
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error inserting recipe: ${e.message}")
        }
    }
}
