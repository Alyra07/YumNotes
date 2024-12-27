package at.mirjam.yumnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.data.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    // Sample list of recipes (can be loaded from the database)
    val recipes = repository.getAllRecipes()

    // Example CRUD operation methods
    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.insertRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }
}