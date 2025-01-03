package at.mirjam.yumnotes.data

import android.content.Context
import android.net.Uri
import android.util.Log
import at.mirjam.yumnotes.util.FileUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// RecipeRepository handles recipe interactions with the database :)
class RecipeRepository(private val recipeDao: RecipeDao) {

    // CREATE
    suspend fun insertRecipe(recipe: Recipe, context: Context) {
        try {
            // Save the recipe image to internal storage
            val recipeWithImagePath = recipe.copy(
                imageUri = recipe.imageUri?.let {
                    FileUtil.saveImageToInternalStorage(context, Uri.parse(it))
                }
            )
            recipeDao.insertRecipe(recipeWithImagePath) // Insert recipe with image path
            Log.d("RecipeRepository", "Recipe inserted successfully: $recipeWithImagePath")
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error inserting recipe: ${e.message}")
        }
    }

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

    // UPDATE
    suspend fun updateRecipe(recipe: Recipe) {
        try {
            recipeDao.updateRecipe(recipe)
            Log.d("RecipeRepository", "Recipe updated successfully: $recipe")
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error updating recipe: ${e.message}")
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
}
