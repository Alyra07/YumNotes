package at.mirjam.yumnotes.content

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.R
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.util.HeaderWithLogo
import at.mirjam.yumnotes.util.tagIcons
import at.mirjam.yumnotes.viewmodel.RecipeViewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun AddRecipeScreen(recipeViewModel: RecipeViewModel) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var ingredients by remember { mutableStateOf(TextFieldValue("")) }
    var instructions by remember { mutableStateOf(TextFieldValue("")) }
    var collectionTags by remember { mutableStateOf(TextFieldValue("")) }
    val selectedTags = remember { mutableStateOf(setOf<String>()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher to pick an image from gallery
    val getImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeaderWithLogo(heading = "Add New Recipe")
        }

        item { // recipe.name
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Recipe Name") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }

        item { // Select Image Section
            imageUri?.let {
                // Display selected image
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Selected Recipe Image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            } ?: Image( // Placeholder image for when no image is selected
                painter = painterResource(id = R.drawable.placeholder_img),
                contentDescription = "Placeholder Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
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

        // Recipe fields
        item {
            TextField( // recipe.ingredients
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients (comma-separated)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
            TextField( // recipe.instructions
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Instructions") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }

        // Predefined Tags Section
        item {
            Text(text = "Category Tags:",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp))

            // Toggleable tagIcons
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tagIcons.forEach { (tag, iconRes) ->
                    Button(
                        onClick = {
                            selectedTags.value = if (selectedTags.value.contains(tag)) {
                                selectedTags.value - tag // Remove tag
                            } else {
                                selectedTags.value + tag // Add tag
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTags.value.contains(tag))
                                MaterialTheme.colorScheme.secondary
                            else
                                MaterialTheme.colorScheme.surface,
                            contentColor = if (selectedTags.value.contains(tag))
                                MaterialTheme.colorScheme.onSecondary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Image( // Category icon
                            painter = painterResource(id = iconRes),
                            contentDescription = "$tag Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = tag, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }

        item { // Custom collection tags field
            TextField( // recipe.collectionTags
            value = collectionTags,
            onValueChange = { collectionTags = it },
            label = { Text("Custom Tags (comma-separated)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
        ) }

        item { // Save Recipe Button
            Button(
                onClick = {
                    if (name.text.isNotEmpty() && ingredients.text.isNotEmpty() && instructions.text.isNotEmpty()) {
                        // add new recipe entry to the database
                        val newRecipe = Recipe(
                            name = name.text,
                            ingredients = ingredients.text,
                            instructions = instructions.text,
                            collectionTags = collectionTags.text,
                            selectedTags = selectedTags.value.joinToString(","),
                            imageUri = imageUri?.toString()
                        )
                        recipeViewModel.addRecipe(newRecipe)

                        // Clear input fields after saving
                        name = TextFieldValue("")
                        ingredients = TextFieldValue("")
                        instructions = TextFieldValue("")
                        collectionTags = TextFieldValue("")
                        selectedTags.value = emptySet()
                        imageUri = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Recipe")
            }
        }
    }
}