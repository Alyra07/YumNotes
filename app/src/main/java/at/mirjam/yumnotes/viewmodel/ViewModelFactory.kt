package at.mirjam.yumnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.mirjam.yumnotes.data.RecipeRepository

class YumNotesViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YumNotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return YumNotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
