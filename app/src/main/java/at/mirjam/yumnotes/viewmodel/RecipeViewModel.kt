package at.mirjam.yumnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.data.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    val recipes = repository.getAllRecipes() // Observe recipes from the repository

    fun addSampleRecipes() {
        viewModelScope.launch {
            repository.insertRecipe(Recipe(name = "Spaghetti Carbonara", ingredients = "Spaghetti, Eggs, Parmesan, Bacon", instructions = "Cook pasta. Mix eggs and cheese. Fry bacon. Combine all."))
            repository.insertRecipe(Recipe(name = "Chocolate Cake", ingredients = "Flour, Cocoa Powder, Eggs, Sugar, Butter", instructions = "Mix ingredients. Bake at 180°C for 30 minutes."))
            repository.insertRecipe(Recipe(name = "Caesar Salad", ingredients = "Lettuce, Croutons, Parmesan, Caesar Dressing", instructions = "Toss all ingredients together."))
        }
    }
}