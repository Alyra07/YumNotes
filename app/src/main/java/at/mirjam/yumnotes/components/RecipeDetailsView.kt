package at.mirjam.yumnotes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe

@Composable
fun RecipeDetailsView(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Recipe Name: ${recipe.name}", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Ingredients: ${recipe.ingredients}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Instructions: ${recipe.instructions}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Tags: ${recipe.collectionTags}", style = MaterialTheme.typography.bodySmall)
    }
}
