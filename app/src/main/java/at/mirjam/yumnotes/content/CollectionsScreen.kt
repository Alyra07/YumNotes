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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.util.tagIcons
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun CollectionsScreen(
    recipeViewModel: RecipeViewModel,
    onRecipeClick: (Recipe) -> Unit,
    navController: NavController
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
        // Group & map recipes by collectionTags
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

            // HEADER FOR TAGS (just like HomeScreen)
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
                    // Display predefined tags as clickable icons
                    tagIcons.forEach { (tag, iconRes) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = "$tag Icon",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        // Navigate to CategoryScreen with the selected tag
                                        navController.navigate("category/$tag")
                                    }
                            )
                            Text(text = tag)
                        }
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
        }
    }
}