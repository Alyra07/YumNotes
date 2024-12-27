package at.mirjam.yumnotes.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun CollectionsScreen(recipeViewModel: RecipeViewModel) {
    val recipes = recipeViewModel.recipes.collectAsState(initial = emptyList()).value

    if (recipes.isEmpty()) {
        Text(
            text = "No recipes available. Add some delicious recipes!",
            modifier = Modifier.fillMaxSize()
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(recipes) { recipe ->
                RecipeCollectionItem(recipe)
            }
        }
    }
}

@Composable
fun RecipeCollectionItem(recipe: Recipe) {
    Text(text = "Recipe: ${recipe.name}")
    Text(text = "Ingredients: ${recipe.ingredients}")
    Text(text = "Instructions: ${recipe.instructions}")
}