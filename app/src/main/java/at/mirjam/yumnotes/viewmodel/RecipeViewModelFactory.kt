package at.mirjam.yumnotes.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.mirjam.yumnotes.data.RecipeRepository
import at.mirjam.yumnotes.data.RecipeDatabase

@Suppress("UNCHECKED_CAST")
class RecipeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = RecipeDatabase.getDatabase(context)
        val repository = RecipeRepository(database.recipeDao())
        return RecipeViewModel(repository, context) as T  // Pass context here
    }
}