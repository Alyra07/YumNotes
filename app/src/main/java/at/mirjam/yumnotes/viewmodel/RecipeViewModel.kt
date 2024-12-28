package at.mirjam.yumnotes.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Controls the data flow between the UI and the data layer
class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    init {
        loadRecipes()
    }
    // load all recipes from database
    private fun loadRecipes() {
        viewModelScope.launch {
            try {
                repository.getAllRecipes().collect { recipeList ->
                    Log.d("RecipeViewModel", "Loaded recipes: $recipeList")
                    _recipes.value = recipeList
                }
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error loading recipes: ${e.message}")
                _recipes.value = emptyList()
            }
        }
    }
    // CREATE
    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                repository.insertRecipe(recipe)
                loadRecipes()  // list is refreshed after adding a recipe
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error adding recipe: ${e.message}")
            }
        }
    }
    // DELETE
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                repository.deleteRecipe(recipe)
                loadRecipes() // Refresh the list
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error deleting recipe: ${e.message}")
            }
        }
    }

    // edit a recipe (CollectionsScreen)
    fun updateRecipe(updatedRecipe: Recipe) {
        viewModelScope.launch {
            try {
                repository.updateRecipe(updatedRecipe)
                loadRecipes() // Refresh the list
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error updating recipe: ${e.message}")
            }
        }
    }
}