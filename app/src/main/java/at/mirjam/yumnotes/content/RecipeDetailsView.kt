package at.mirjam.yumnotes.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe

@Composable
fun RecipeDetailsView(
    recipe: Recipe,
    onDeleteClick: (Recipe) -> Unit,
    onSaveEdit: (Recipe) -> Unit // Callback for saving edited recipe
) {
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing) {
        RecipeEditView(
            recipe = recipe,
            onSaveClick = { updatedRecipe ->
                onSaveEdit(updatedRecipe)
                isEditing = false // Return to details view after saving
            },
            onCancelClick = {
                isEditing = false // Return to details view without saving
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Recipe Name: ${recipe.name}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Ingredients: ${recipe.ingredients}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Instructions: ${recipe.instructions}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Tags: ${recipe.collectionTags}",
                style = MaterialTheme.typography.bodySmall
            )

            // Action buttons for Edit and Delete
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { isEditing = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                Button(onClick = { onDeleteClick(recipe) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}