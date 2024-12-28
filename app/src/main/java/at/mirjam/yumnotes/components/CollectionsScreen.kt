package at.mirjam.yumnotes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun CollectionsScreen(
    recipeViewModel: RecipeViewModel,
    onRecipeClick: (Recipe) -> Unit,
    onEditRecipeClick: (Recipe) -> Unit // Navigate to Edit Recipe Screen
) {
    val recipes = recipeViewModel.recipes.collectAsState(initial = emptyList()).value
    var isEditMode by remember { mutableStateOf(false) }

    Column {
        // Toggle button for Edit Mode
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isEditMode) "Edit Mode" else "View Mode",
                style = MaterialTheme.typography.headlineSmall
            )
            Button(onClick = { isEditMode = !isEditMode }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                Text(text = if (isEditMode) "Done" else "Edit")
            }
        }

        if (recipes.isEmpty()) {
            Text(
                text = "No recipes available. Add some delicious recipes!",
                modifier = Modifier.fillMaxSize()
            )
        } else {
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
                        if (isEditMode) {
                            RecipeEditListItem(
                                recipe = recipe,
                                onEditClick = { onEditRecipeClick(recipe) },
                                onDeleteClick = { recipeViewModel.deleteRecipe(recipe) }
                            )
                        } else {
                            RecipeListItem(recipe = recipe, onClick = onRecipeClick)
                        }
                    }
                }
            }
        }
    }
}