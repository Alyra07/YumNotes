package at.mirjam.yumnotes.content

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.util.tagIcons
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import at.mirjam.yumnotes.R
import coil.compose.rememberAsyncImagePainter
import java.io.File

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun RecipeEditView(
    recipe: Recipe,
    onSaveClick: (Recipe) -> Unit,
    onCancelClick: () -> Unit
) {
    var name by remember { mutableStateOf(recipe.name) }
    var ingredients by remember { mutableStateOf(recipe.ingredients) }
    var instructions by remember { mutableStateOf(recipe.instructions) }
    var tags by remember { mutableStateOf(recipe.collectionTags) }
    // Initialize selectedTags by splitting the string from the recipe data
    var selectedTags by remember { mutableStateOf(recipe.selectedTags.split(",").toMutableSet()) }

    // Initialize imageUri with the existing imageUri from the recipe or null if empty
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    // Launcher to pick an image from gallery
    val getImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val context = LocalContext.current
    LaunchedEffect(recipe) {
        // Construct the full file path for the image
        imageUri = recipe.imageUri?.let {
            // Get the full path by using the app's internal storage directory
            File(context.filesDir, it).let { file ->
                if (file.exists()) {
                    Uri.fromFile(file)
                } else {
                    null // If the file doesn't exist, fallback to null
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Edit Recipe",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item { // Select Image
            Column (
                Modifier.fillMaxWidth(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                imageUri?.let {
                    Image( // If imageUri is not null, use it
                        painter = rememberAsyncImagePainter(
                            model = it,
                            placeholder = painterResource(id = R.drawable.placeholder_img),
                            error = painterResource(id = R.drawable.placeholder_img)
                        ),
                        contentDescription = "Selected Recipe Image",
                        modifier = Modifier.size(200.dp).clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.placeholder_img), // Use placeholder image if no URI
                    contentDescription = "Placeholder Image",
                    modifier = Modifier.size(200.dp).clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Button(
                    onClick = { getImage.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Text("Select Image")
                }
            }
        }

        item { // Recipe fields
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Recipe Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = ingredients,
                    onValueChange = { ingredients = it },
                    label = { Text("Ingredients") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Instructions") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = tags,
                    onValueChange = { tags = it },
                    label = { Text("Collection Tags") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item { // Category Tags Section
            Text(text = "Category Tags:", style = MaterialTheme.typography.bodySmall)
            FlowRow( // adjusts category icons flexibly
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                tagIcons.forEach { (tag, iconRes) ->
                    Button(
                        onClick = {
                            // Update selectedTags state with a new MutableSet
                            selectedTags = selectedTags.toMutableSet().apply {
                                if (contains(tag)) {
                                    remove(tag)
                                } else {
                                    add(tag)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTags.contains(tag))
                                MaterialTheme.colorScheme.secondary
                            else
                                MaterialTheme.colorScheme.surface,
                            contentColor = if (selectedTags.contains(tag))
                                MaterialTheme.colorScheme.onSecondary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Image( // Category Icon
                            painter = painterResource(id = iconRes),
                            contentDescription = "$tag Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = tag,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
        item { // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button( // Save
                    onClick = {
                        val updatedRecipe = recipe.copy(
                            name = name,
                            ingredients = ingredients,
                            instructions = instructions,
                            collectionTags = tags,
                            // Convert selectedTags back to a string when saving
                            selectedTags = selectedTags.joinToString(","),
                            imageUri = imageUri?.toString(), // Convert Uri to String
                        )
                        onSaveClick(updatedRecipe)
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("Save")
                }

                Button( // Cancel
                    onClick = onCancelClick,
                    modifier = Modifier.padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}