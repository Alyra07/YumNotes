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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeDetailsView(
    recipe: Recipe,
    onDeleteClick: (Recipe) -> Unit,
    onSaveEdit: (Recipe) -> Unit
) {
    val context = LocalContext.current // Get the current context
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing) {
        RecipeEditView(
            recipe = recipe,
            onSaveClick = { updatedRecipe ->
                onSaveEdit(updatedRecipe)
                isEditing = false
            },
            onCancelClick = { isEditing = false }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Recipe Image
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

            // Recipe Details
            Text(text = "Recipe Name: ${recipe.name}", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Ingredients: ${recipe.ingredients}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Instructions: ${recipe.instructions}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Collections: ${recipe.collectionTags}", style = MaterialTheme.typography.bodyMedium)

            // Predefined Tags Section with FlowRow
            Text(text = "Category Tags:", style = MaterialTheme.typography.bodySmall)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 6,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                overflow = FlowRowOverflow.Clip
            ) {
                recipe.selectedTags.split(",").forEach { tag ->
                    val iconRes = tagIcons[tag]
                    if (iconRes != null) {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = "$tag Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = tag,
                            modifier = Modifier.padding(end = 16.dp))
                    } else {
                        Text(text = tag) // Fallback if no icon is available
                    }
                }
            }

            // Action buttons (Edit & Delete)
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