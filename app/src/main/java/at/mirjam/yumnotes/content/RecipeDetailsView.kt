package at.mirjam.yumnotes.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.util.tagIcons
import coil.compose.rememberAsyncImagePainter
import java.io.File
import androidx.navigation.NavController

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeDetailsView(
    recipe: Recipe,
    onDeleteClick: (Recipe) -> Unit,
    onSaveEdit: (Recipe) -> Unit,
    navController: NavController
) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { // Header - Recipe Image and Back Button
                IconButton( // Back Button
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                Image( // Recipe Image
                    painter = rememberAsyncImagePainter(
                        File(
                            LocalContext.current.filesDir,
                            recipe.imageUri ?: ""
                        )
                    ),
                    contentDescription = "Recipe Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                )
            }

            item { // Recipe Name and Edit/Delete buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box( // take full width
                        modifier = Modifier.weight(1f)
                    ) {
                        Text( // Recipe Name
                            text = recipe.name,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Clip // clip text if it's too long
                        )
                    }
                    Row( // Buttons
                        horizontalArrangement = Arrangement.spacedBy(28.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton( // Edit
                            onClick = { isEditing = true },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit recipe",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        IconButton( // Delete
                            onClick = { onDeleteClick(recipe) },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.error)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Recipe",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }

            item { // Category Tags with icons
                FlowRow( // aligns icons flexibly in a row
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 6, // icon + tag = 2 items
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    recipe.selectedTags.split(",").forEach { tag ->
                        val trimmedTag = tag.trim()
                        val iconRes = tagIcons[trimmedTag]
                        if (iconRes != null) {
                            // display icon & name for each given tag
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = "$trimmedTag Icon",
                                modifier = Modifier.size(26.dp)
                            )
                            Text(
                                text = trimmedTag,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        } else {
                            Text(text = trimmedTag, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Other recipe details
            item {
                Text(
                    text = "Ingredients:",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 2,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Display ingredients in a FlowRow in two columns (split by comma)
                    recipe.ingredients.split(",").forEach { ingredient ->
                        Text(
                            text = ingredient.trim(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f) // text spans the whole width
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Text(
                    text = "Instructions:",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = recipe.instructions,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}