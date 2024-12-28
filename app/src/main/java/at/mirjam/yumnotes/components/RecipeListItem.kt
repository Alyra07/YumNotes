package at.mirjam.yumnotes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe
import coil.compose.rememberAsyncImagePainter // Coil for loading images
import java.io.File
import androidx.compose.ui.platform.LocalContext

@Composable
fun RecipeListItem(
    recipe: Recipe,
    onClick: (Recipe) -> Unit
) {
    val context = LocalContext.current // Get the current context

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(recipe) }
    ) {
        // Display the image as a header
        recipe.imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(File(context.filesDir, it)), // Use context here
                contentDescription = "Recipe image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // Add space between the image and text

        // Recipe Name
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.headlineSmall
        )

        // Collection Tags
        Text(
            text = "Tags: ${recipe.collectionTags}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}