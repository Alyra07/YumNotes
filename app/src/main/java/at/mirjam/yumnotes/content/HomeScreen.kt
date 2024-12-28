package at.mirjam.yumnotes.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.util.tagIcons
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(
    recipeViewModel: RecipeViewModel,
    onRecipeClick: (Recipe) -> Unit,
    navController: NavController
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
            // HEADLINE HOME SCREEN
            item {
                Text(
                    text = "YumNotes - Home",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            // PREDEFINED TAGS (CATEGORIES)
            item {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Custom /res/drawable tagIcons
                    tagIcons.forEach { (tag, iconRes) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable {
                                // Navigate to CategoryScreen with the selected tag
                                navController.navigate("category/$tag")
                            }
                        ) {
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = "$tag Icon",
                                modifier = Modifier.size(48.dp)
                            )
                            Text(text = tag)
                        }
                    }
                }
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

            // ALL RECIPES
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