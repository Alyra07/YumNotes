package at.mirjam.yumnotes.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.data.RecipeRepository
import kotlinx.coroutines.launch

class YumNotesViewModel(private val repository: RecipeRepository) : ViewModel() {

    // State variables
//    var recipes by mutableStateOf<List<Recipe>>(emptyList())
//    private set

    var selectedTab by mutableStateOf(Tab.HOME)
        private set

    private var searchQuery by mutableStateOf("")

    // Enum for tabs
    enum class Tab {
        HOME, LIST, ADD_RECIPE, COLLECTIONS, PROFILE
    }

//    init {
//        fetchRecipes()
//    }
//
//    // Functions to interact with the database
//    private fun fetchRecipes() {
//        viewModelScope.launch {
//            recipes = repository.getAllRecipes()
//        }
//    }
//
//    fun addRecipe(recipe: Recipe) {
//        viewModelScope.launch {
//            repository.insertRecipe(recipe)
//            fetchRecipes()
//        }
//    }
//
//    fun updateRecipe(recipe: Recipe) {
//        viewModelScope.launch {
//            repository.updateRecipe(recipe)
//            fetchRecipes()
//        }
//    }
//
//    fun deleteRecipe(recipe: Recipe) {
//        viewModelScope.launch {
//            repository.deleteRecipe(recipe)
//            fetchRecipes()
//        }
//    }

    // Tab navigation functions
    fun selectTab(tab: Tab) {
        selectedTab = tab
    }

    // Search functionality
//    fun updateSearchQuery(query: String) {
//        searchQuery = query
//    }
//
//    fun getFilteredRecipes(): List<Recipe> {
//        return if (searchQuery.isEmpty()) recipes
//        else recipes.filter { it.name.contains(searchQuery, ignoreCase = true) }
//    }
}
