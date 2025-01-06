package at.mirjam.yumnotes.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.util.HeaderWithLogo
import at.mirjam.yumnotes.util.RecipeListItem
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun CategoryScreen(
    recipeViewModel: RecipeViewModel,
    tag: String,
    onRecipeClick: (Recipe) -> Unit
) {
    val recipes by recipeViewModel.recipes.collectAsState(initial = emptyList())

    // Filter recipes by the selected tag
    val filteredRecipes = recipes.filter { it.selectedTags.contains(tag) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { // HEADING & LOGO
            HeaderWithLogo(heading = "$tag Recipes")
        }

        if (filteredRecipes.isEmpty()){ // No recipes found for selectedTags
            item {
                Text(
                    text = "No recipes available in this category!"
                )
            }
        } else {
            // Display recipes filtered by category
            items(filteredRecipes) { recipe ->
                RecipeListItem(recipe = recipe, onClick = onRecipeClick)
            }
        }
    }
}