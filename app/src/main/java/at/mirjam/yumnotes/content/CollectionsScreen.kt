package at.mirjam.yumnotes.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.util.CategoryIconRow
import at.mirjam.yumnotes.util.RecipeListItem
import at.mirjam.yumnotes.util.SearchBar
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun CollectionsScreen(
    recipeViewModel: RecipeViewModel,
    onRecipeClick: (Recipe) -> Unit,
    navController: NavController
) {
    val recipes = recipeViewModel.recipes.collectAsState(initial = emptyList()).value

    // SearchBar search query state
    val searchQuery = remember { mutableStateOf("") }

    // Filtered recipes based on collectionTags
    val filteredRecipes = if (searchQuery.value.isNotEmpty()) {
        recipes.filter { recipe ->
            recipe.collectionTags.split(",").any { tag ->
                tag.trim().contains(searchQuery.value, ignoreCase = true)
            }
        }
    } else {
        recipes
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // HEADING
        item {
            Text(
                text = "Your Collections",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        // SEARCH BAR
        item {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = {},
                placeholderText = "Search Collection Tags..."
            )
        }

        // PREDEFINED TAGS (Categories)
        item {
            // Category Icons
            CategoryIconRow(navController = navController)
        }

        if (filteredRecipes.isEmpty()) {
            item {
                Text(
                    text = "No recipes found for the given tag.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            // Group & map filtered recipes by collectionTags
            val tagToRecipesMap = mutableMapOf<String, MutableList<Recipe>>()
            val otherRecipes = mutableListOf<Recipe>() // For recipes without collectionTags

            filteredRecipes.forEach { recipe ->
                val tags = recipe.collectionTags.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                if (tags.isEmpty()) {
                    otherRecipes.add(recipe)
                } else {
                    tags.forEach { tag ->
                        tagToRecipesMap.getOrPut(tag) { mutableListOf() }.add(recipe)
                    }
                }
            }

            // Display recipes grouped by collectionTags
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

            // Display recipes without collectionTags under "Other Recipes"
            if (otherRecipes.isNotEmpty()) {
                item {
                    Text(
                        text = "Other Recipes",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                items(otherRecipes) { recipe ->
                    RecipeListItem(recipe = recipe, onClick = onRecipeClick)
                }
            }
        }
    }
}