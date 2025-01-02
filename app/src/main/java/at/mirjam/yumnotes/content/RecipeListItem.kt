package at.mirjam.yumnotes.content

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun RecipeListItem(
    recipe: Recipe,
    onClick: (Recipe) -> Unit
) {
    val context = LocalContext.current // Get the current context (recipe data)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(recipe) }
    ) {
        // Header image
        recipe.imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(File(context.filesDir, it)),
                contentDescription = "Recipe image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // image height
                    .clip(RoundedCornerShape(16.dp)) // Rounded corners
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop // image is cropped to fill the space
            )
        }

        // Recipe Name
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        // Collection Tags
        if (recipe.collectionTags.isNotEmpty()) {
            Text(
                text = "Tags: ${recipe.collectionTags}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
}