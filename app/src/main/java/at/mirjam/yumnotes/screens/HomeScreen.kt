package at.mirjam.yumnotes.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(recipeViewModel: RecipeViewModel) {
    Text(text = "Welcome to YumNotes!", modifier = Modifier.fillMaxSize())
}
