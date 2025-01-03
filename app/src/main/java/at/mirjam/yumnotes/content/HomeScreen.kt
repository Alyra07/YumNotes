package at.mirjam.yumnotes.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

// Adjusted HomeScreen
@Composable
fun HomeScreen(
    recipeViewModel: RecipeViewModel,
    onRecipeClick: (Recipe) -> Unit,
    navController: NavController
) {
    val recipes by recipeViewModel.recipes.collectAsState(initial = emptyList())

    val searchQuery = remember { mutableStateOf("") }

    val filteredRecipes = if (searchQuery.value.isNotEmpty()) {
        recipes.filter { it.name.contains(searchQuery.value, ignoreCase = true) }
    } else {
        recipes
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { // HEADING
            Text(
                text = "YumNotes - Recipes",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item { // SEARCH BAR
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { query -> searchQuery.value = query }
            )
        }
        // CATEGORY ICON ROW
        item { CategoryIconRow(navController = navController) }

        if (filteredRecipes.isEmpty()) { // No recipes found
            item {
                Text(
                    text = "No recipes found.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            // FEATURED RECIPE SECTION
            val featuredRecipe = recipes.firstOrNull()
            featuredRecipe?.let {
                item {
                    Text(
                        text = "Featured Recipe",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                // Display featured RecipeListItem
                item { RecipeListItem(recipe = it, onClick = onRecipeClick) }
            }

            item { // ALL RECIPES SECTION
                Text(
                    text = "All Recipes",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            // Display filtered recipes (based on search query)
            items(filteredRecipes) { recipe ->
                RecipeListItem(recipe = recipe, onClick = onRecipeClick)
            }
        }

    }
}