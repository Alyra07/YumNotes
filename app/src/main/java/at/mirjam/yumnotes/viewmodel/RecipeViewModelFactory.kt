package at.mirjam.yumnotes.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.mirjam.yumnotes.data.RecipeRepository
import at.mirjam.yumnotes.data.RecipeDatabase

class RecipeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = RecipeDatabase.getDatabase(context)
        val repository = RecipeRepository(database.recipeDao())
        return RecipeViewModel(repository) as T
    }
}