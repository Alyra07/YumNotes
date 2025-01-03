package at.mirjam.yumnotes.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.mirjam.yumnotes.data.ProfileRepository
import at.mirjam.yumnotes.data.RecipeRepository
import at.mirjam.yumnotes.data.YumNotesDatabase

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = YumNotesDatabase.getDatabase(context)
        return when {
            // RecipeViewModel class
            modelClass.isAssignableFrom(RecipeViewModel::class.java) -> {
                val repository = RecipeRepository(database.recipeDao())
                RecipeViewModel(repository, context) as T
            }
            // ProfileViewModel class
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                val repository = ProfileRepository(database.profileDao())
                ProfileViewModel(repository, context) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}