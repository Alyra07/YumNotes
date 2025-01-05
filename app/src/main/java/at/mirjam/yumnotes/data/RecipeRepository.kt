package at.mirjam.yumnotes.data

import android.content.Context
import android.net.Uri
import android.util.Log
import at.mirjam.yumnotes.util.FileUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// RecipeRepository handles recipe interactions with the database :)
class RecipeRepository(private val recipeDao: RecipeDao) {

    // CREATE (add recipe)
    suspend fun insertRecipe(recipe: Recipe, context: Context) {
        try {
            // Save the recipe image to internal storage
            val imageUriPath = recipe.imageUri?.let {
                FileUtil.saveImageToInternalStorage(context, Uri.parse(it))
            }
            val recipeWithImagePath = recipe.copy(
                imageUri = imageUriPath, // Save image URI to internal storage
                originalImageUri = imageUriPath // Set originalImageUri to the same as imageUri
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

    // UPDATE (edit recipes)
    suspend fun updateRecipe(recipe: Recipe, context: Context) {
        try {
            // only save the image if the imageUri has changed and is different from originalImageUri
            val updatedRecipe = if (recipe.imageUri != recipe.originalImageUri) {
                // Save the new image to internal storage if imageUri is different
                val newImageUri = recipe.imageUri?.let {
                    FileUtil.saveImageToInternalStorage(context, Uri.parse(it))
                }
                // Update the recipe with the new imageUri
                recipe.copy(
                    imageUri = newImageUri,
                    originalImageUri = newImageUri // Update originalImageUri to match the new path
                )
            } else {
                // If no image change, keep the recipe as it is
                recipe
            }
            // Update the recipe data
            recipeDao.updateRecipe(updatedRecipe)
            Log.d("RecipeRepository", "Recipe updated successfully: $updatedRecipe")
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
