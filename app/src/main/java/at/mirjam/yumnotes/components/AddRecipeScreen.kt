package at.mirjam.yumnotes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun AddRecipeScreen(recipeViewModel: RecipeViewModel) {

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var ingredients by remember { mutableStateOf(TextFieldValue("")) }
    var instructions by remember { mutableStateOf(TextFieldValue("")) }
    var collectionTags by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Add a New Recipe", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Recipe Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients (comma-separated)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("Instructions") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = collectionTags,
            onValueChange = { collectionTags = it },
            label = { Text("Collection Tags (comma-separated)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        Button(
            onClick = {
                if (name.text.isNotEmpty() && ingredients.text.isNotEmpty() && instructions.text.isNotEmpty()) {
                    val newRecipe = Recipe(
                        name = name.text,
                        ingredients = ingredients.text,
                        instructions = instructions.text,
                        collectionTags = collectionTags.text
                    )
                    recipeViewModel.addRecipe(newRecipe)

                    // Clear fields after saving
                    name = TextFieldValue("")
                    ingredients = TextFieldValue("")
                    instructions = TextFieldValue("")
                    collectionTags = TextFieldValue("")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Recipe")
        }
    }
}