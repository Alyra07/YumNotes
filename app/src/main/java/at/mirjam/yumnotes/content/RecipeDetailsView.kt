package at.mirjam.yumnotes.content

import android.util.Log
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
    val context = LocalContext.current // Get the current context
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing) { // show RecipeEditView if isEditing is true
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
            item { // Recipe Image
                recipe.imageUri?.let {
                    Log.d("RecipeDetailsView", "Image URI: $it") // check URI
                    Image(
                        painter = rememberAsyncImagePainter(File(context.filesDir, it)),
                        contentDescription = "Recipe Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }
            }
            item { // Recipe Name
                Text(
                    text = "Recipe Name: ${recipe.name}",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item { // Recipe Details
                Text(
                    text = "Ingredients: ${recipe.ingredients}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Instructions: ${recipe.instructions}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Collections: ${recipe.collectionTags}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            item {  // Predefined Tags Section
                Text(text = "Category Tags:", style = MaterialTheme.typography.bodySmall)
                FlowRow( // layout that fills items from left to right
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 6, // text + icon = 2 items
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    recipe.selectedTags.split(",").forEach { tag ->
                        val trimmedTag = tag.trim() // Trim spaces to avoid issues with matching
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
                            Text(text = trimmedTag) // Fallback if no icon is available
                        }
                    }
                }
            }
            item {  // Action buttons (Edit & Delete)
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