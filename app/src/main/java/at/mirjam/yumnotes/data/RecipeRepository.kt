package at.mirjam.yumnotes.data

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

// RecipeRepository handles interactions with the database :)
class RecipeRepository(private val recipeDao: RecipeDao) {

    // CREATE
    suspend fun insertRecipe(recipe: Recipe, context: Context) {
        try {
            // Save recipe image to local storage and get the path
            recipe.imageUri?.let {
                val imagePath = saveImageToInternalStorage(context, Uri.parse(it))
                val recipeWithImagePath = recipe.copy(imageUri = imagePath)
                recipeDao.insertRecipe(recipeWithImagePath) // Insert recipe with image
                Log.d("RecipeRepository", "Recipe inserted successfully: $recipeWithImagePath")
            } ?: run {
                recipeDao.insertRecipe(recipe) // Insert recipe without image
                Log.d("RecipeRepository", "Recipe inserted without image: $recipe")
            }
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

    // Function to save image to internal storage, accept Context as a parameter
    private fun saveImageToInternalStorage(context: Context, uri: Uri): String {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return fileName
    }
}
