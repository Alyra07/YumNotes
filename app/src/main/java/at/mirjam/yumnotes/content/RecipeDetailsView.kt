package at.mirjam.yumnotes.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.util.tagIcons
import coil.compose.rememberAsyncImagePainter
import java.io.File
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeDetailsView(
    recipe: Recipe,
    onDeleteClick: (Recipe) -> Unit,
    onSaveEdit: (Recipe) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing) {
        // Edit mode
        RecipeEditView(
            recipe = recipe,
            onSaveClick = { updatedRecipe ->
                onSaveEdit(updatedRecipe)
                isEditing = false
            },
            onCancelClick = { isEditing = false }
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Image( // Display recipe image
                    painter = rememberAsyncImagePainter(File(LocalContext.current.filesDir, recipe.imageUri ?: "")),
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            item {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                Text(
                    text = "Ingredients:",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = recipe.ingredients,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Instructions:",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = recipe.instructions,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item {
                Text(text = "Category Tags:", style = MaterialTheme.typography.headlineSmall)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 6,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    recipe.selectedTags.split(",").forEach { tag ->
                        val trimmedTag = tag.trim()
                        val iconRes = tagIcons[trimmedTag]
                        if (iconRes != null) {
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = "$trimmedTag Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = trimmedTag,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        } else {
                            Text(text = trimmedTag)
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { isEditing = true }) {
                        Text(text = "Edit")
                    }
                    Button(onClick = { onDeleteClick(recipe) }) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}