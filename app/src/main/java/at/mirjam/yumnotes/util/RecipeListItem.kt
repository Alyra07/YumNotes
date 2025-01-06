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
import coil.compose.rememberAsyncImagePainter // for loading images
import java.io.File
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import at.mirjam.yumnotes.R

@Composable
fun RecipeListItem(
    recipe: Recipe,
    onClick: (Recipe) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable { onClick(recipe) }
            .padding(bottom = 12.dp)
    ) {
        Column {
            // Recipe Image
            Image(
                painter = rememberAsyncImagePainter(
                    model = recipe.imageUri?.let { File(context.filesDir, it) },
                    // show placeholder when no image / loading
                    placeholder = painterResource(id = R.drawable.placeholder_img),
                    error = painterResource(id = R.drawable.placeholder_img)
                ),
                contentDescription = "Recipe image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Recipe Name
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Tags
            if (recipe.collectionTags.isNotEmpty()) {
                Text(
                    text = "Collections: ${recipe.collectionTags}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}