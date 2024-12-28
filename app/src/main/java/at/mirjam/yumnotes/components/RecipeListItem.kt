package at.mirjam.yumnotes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
