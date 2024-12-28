package at.mirjam.yumnotes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe

@Composable
fun RecipeListItem(
    recipe: Recipe,
    onClick: (Recipe) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(recipe) }
    ) {
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Tags: ${recipe.collectionTags}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RecipeEditListItem(
    recipe: Recipe,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Tags: ${recipe.collectionTags}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Button(onClick = onEditClick, modifier = Modifier.padding(end = 8.dp)) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }
        Button(onClick = onDeleteClick) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
