package at.mirjam.yumnotes.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable { onClick(recipe) }
            .padding(16.dp)
    ) {
        Column {
            // Recipe Image
            recipe.imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(File(context.filesDir, it)),
                    contentDescription = "Recipe image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(bottom = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Recipe Name
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Tags
            if (recipe.collectionTags.isNotEmpty()) {
                Text(
                    text = "Tags: ${recipe.collectionTags}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}