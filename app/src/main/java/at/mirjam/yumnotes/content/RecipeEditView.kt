package at.mirjam.yumnotes.content

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.util.tagIcons
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun RecipeEditView(
    recipe: Recipe,
    onSaveClick: (Recipe) -> Unit,
    onCancelClick: () -> Unit
) {
    var name by remember { mutableStateOf(recipe.name) }
    var ingredients by remember { mutableStateOf(recipe.ingredients) }
    var instructions by remember { mutableStateOf(recipe.instructions) }
    var tags by remember { mutableStateOf(recipe.collectionTags) }
    // Initialize selectedTags by splitting the string from the recipe data
    var selectedTags by remember { mutableStateOf(recipe.selectedTags.split(",").toMutableSet()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Edit Recipe",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item { // Recipe fields
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Recipe Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = ingredients,
                    onValueChange = { ingredients = it },
                    label = { Text("Ingredients") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Instructions") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = tags,
                    onValueChange = { tags = it },
                    label = { Text("Collection Tags") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item { // Predefined Tags Section
            Text(text = "Category Tags:", style = MaterialTheme.typography.bodySmall)
            FlowRow(
                // adjusts category icons flexibly
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                tagIcons.forEach { (tag, iconRes) ->
                    Button(
                        onClick = {
                            // Update selectedTags state with a new MutableSet
                            selectedTags = selectedTags.toMutableSet().apply {
                                if (contains(tag)) {
                                    remove(tag)
                                } else {
                                    add(tag)
                                }
                            }
                        },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = if (selectedTags.contains(tag))
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surface,
                            contentColor = if (selectedTags.contains(tag))
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = "$tag Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = tag,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        item { // Buttons section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        val updatedRecipe = recipe.copy(
                            name = name,
                            ingredients = ingredients,
                            instructions = instructions,
                            collectionTags = tags,
                            // Convert selectedTags back to a string when saving
                            selectedTags = selectedTags.joinToString(",")
                        )
                        onSaveClick(updatedRecipe)
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("Save")
                }

                Button(
                    onClick = onCancelClick,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}