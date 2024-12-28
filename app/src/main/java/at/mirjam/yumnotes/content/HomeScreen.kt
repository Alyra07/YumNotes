package at.mirjam.yumnotes.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(
    recipeViewModel: RecipeViewModel,
    onRecipeClick: (Recipe) -> Unit
) {
    val recipes by recipeViewModel.recipes.collectAsState(initial = emptyList())

    if (recipes.isEmpty()) {
        Text(
            text = "No recipes available.",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Headline for HomeScreen
            item {
                Text(
                    text = "YumNotes - Home",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            // Display a random recipe at the top
            recipes.randomOrNull()?.let { randomRecipe ->
                item {
                    Text(
                        text = "Featured Recipe",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    RecipeListItem(recipe = randomRecipe, onClick = onRecipeClick)
                }
            }

            // Group recipes by collection tags
            val tagToRecipesMap = mutableMapOf<String, MutableList<Recipe>>()
            recipes.forEach { recipe ->
                recipe.collectionTags.split(",").map { it.trim() }.forEach { tag ->
                    tagToRecipesMap.getOrPut(tag) { mutableListOf() }.add(recipe)
                }
            }

            // Most popular collection (tag with the most recipes)
            val mostPopularCollection = tagToRecipesMap.maxByOrNull { it.value.size }

            mostPopularCollection?.let { (tag, recipesForTag) ->
                item {
                    Text(
                        text = "Most Popular Collection: $tag",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(recipesForTag) { recipe ->
                            RecipeListItem(recipe = recipe, onClick = onRecipeClick)
                        }
                    }
                }
            }

            // Display all recipes
            item {
                Text(
                    text = "All Recipes",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            items(recipes) { recipe ->
                RecipeListItem(recipe = recipe, onClick = onRecipeClick)
            }
        }
    }
}