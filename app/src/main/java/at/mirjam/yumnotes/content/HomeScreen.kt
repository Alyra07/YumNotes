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
import at.mirjam.yumnotes.util.SearchBar
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(
    recipeViewModel: RecipeViewModel,
    onRecipeClick: (Recipe) -> Unit,
    navController: NavController
) {
    val recipes by recipeViewModel.recipes.collectAsState(initial = emptyList())

    // SearchBar search query state
    val searchQuery = remember { mutableStateOf("") }

    // Filtered recipes based on search query
    val filteredRecipes = if (searchQuery.value.isNotEmpty()) {
        recipes.filter { it.name.contains(searchQuery.value, ignoreCase = true) }
    } else {
        emptyList() // Empty list when nothing is typed
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // HEADING HOME SCREEN
        item {
            Text(
                text = "YumNotes - Recipes",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        // SEARCH BAR
        item {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { query ->
                    searchQuery.value = query // Update search query
                }
            )
        }

        // If there's no search query, display the default content
        if (searchQuery.value.isEmpty()) {

            // PREDEFINED TAGS (CATEGORIES)
            item {
                // Category Icons
                CategoryIconRow(navController = navController)
            }

            // RANDOM RECIPE
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

            // DISPLAY ALL RECIPES
            item {
                Text(
                    text = "All Recipes",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // List of all recipes
            items(recipes) { recipe ->
                RecipeListItem(recipe = recipe, onClick = onRecipeClick)
            }
        } else {
            // If there is a search query, only show matching recipes
            if (filteredRecipes.isEmpty()) {
                item {
                    Text(
                        text = "No results found.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                // List of filtered recipes
                items(filteredRecipes) { recipe ->
                    RecipeListItem(recipe = recipe, onClick = onRecipeClick)
                }
            }
        }
    }
}