package at.mirjam.yumnotes.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun AddRecipeScreen(recipeViewModel: RecipeViewModel) {
    Text(text = "Add a new recipe here!", modifier = Modifier.fillMaxSize())
}
