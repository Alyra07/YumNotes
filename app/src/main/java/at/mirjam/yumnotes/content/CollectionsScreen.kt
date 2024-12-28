package at.mirjam.yumnotes.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun CollectionsScreen(
    recipeViewModel: RecipeViewModel,
    onRecipeClick: (Recipe) -> Unit
) {
    val recipes = recipeViewModel.recipes.collectAsState(initial = emptyList()).value

    if (recipes.isEmpty()) {
        Text(
            text = "No recipes available. Add some delicious recipes!",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    } else {
        // Group & map recipes by tags
        val tagToRecipesMap = mutableMapOf<String, MutableList<Recipe>>()
        recipes.forEach { recipe ->
            recipe.collectionTags.split(",").map { it.trim() }.forEach { tag ->
                tagToRecipesMap.getOrPut(tag) { mutableListOf() }.add(recipe)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tagToRecipesMap.forEach { (tag, recipesForTag) ->
                item {
                    Text(
                        text = "Collection: $tag",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                items(recipesForTag) { recipe ->
                    RecipeListItem(recipe = recipe, onClick = onRecipeClick)
                }
            }
        }
    }
}