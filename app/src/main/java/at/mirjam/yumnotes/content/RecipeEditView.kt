package at.mirjam.yumnotes.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import at.mirjam.yumnotes.data.Recipe
import androidx.compose.material3.TextField

@Composable
fun RecipeEditView(
    recipe: Recipe,
    onSaveClick: (Recipe) -> Unit,
    onCancelClick: () -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue(recipe.name)) }
    var ingredients by remember { mutableStateOf(TextFieldValue(recipe.ingredients)) }
    var instructions by remember { mutableStateOf(TextFieldValue(recipe.instructions)) }
    var tags by remember { mutableStateOf(TextFieldValue(recipe.collectionTags)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Edit Recipe",
            style = MaterialTheme.typography.headlineSmall
        )

        // Adjust the TextField modifiers to prevent them from occupying the entire screen.
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
            label = { Text("Tags") },
            modifier = Modifier.fillMaxWidth()
        )

        // Buttons section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    val updatedRecipe = recipe.copy(
                        name = name.text,
                        ingredients = ingredients.text,
                        instructions = instructions.text,
                        collectionTags = tags.text
                    )
                    onSaveClick(updatedRecipe)
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Save")
            }

            Button(
                onClick = onCancelClick,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Cancel")
            }
        }
    }
}