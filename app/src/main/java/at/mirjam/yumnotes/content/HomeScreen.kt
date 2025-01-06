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
import at.mirjam.yumnotes.util.HeaderWithLogo

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
        item { // HEADING & LOGO
            HeaderWithLogo(heading = "YumNotes")
        }

        // Search Bar
        item {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { query -> searchQuery.value = query }
            )
        }
        // Category Icon Row
        item {
            CategoryIconRow(navController = navController)
        }

        if (filteredRecipes.isEmpty()) {
            // when no recipes match the search query
            if (searchQuery.value.isNotEmpty()) {
                item {
                    Text(
                        text = "No recipes found matching your search.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                // when there are no recipes, and nothing is searched
                item {
                    Text(
                        text = "No recipes found. Add some now! :)",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            // Show these sections only if no search query is active
            if (searchQuery.value.isEmpty()) {
                // Display a random RecipeListItem as featured recipe
                recipes.randomOrNull()?.let { randomRecipe ->
                    item {
                        Text(
                            text = "You could make ...",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        RecipeListItem(recipe = randomRecipe, onClick = onRecipeClick)
                    }
                }
                // "All Recipes" section
                item {
                    Text(
                        text = "All Recipes",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }

            // Display filtered recipes (based on search query)
            items(filteredRecipes) { recipe ->
                RecipeListItem(recipe = recipe, onClick = onRecipeClick)
            }
        }
    }
}