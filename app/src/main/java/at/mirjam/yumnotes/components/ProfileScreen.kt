package at.mirjam.yumnotes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.data.Recipe
import at.mirjam.yumnotes.viewmodel.RecipeViewModel

@Composable
fun ProfileScreen(recipeViewModel: RecipeViewModel) {
// Observe the recipes from the ViewModel using collectAsState
    val recipes by recipeViewModel.recipes.collectAsState(initial = emptyList())

    // Display a message if no recipes are available, otherwise show the list of recipes
    if (recipes.isEmpty()) {
        Text(
            text = "No recipes available.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes) { recipe ->
                RecipeListTitle(recipe)
            }
        }
    }
}

@Composable
fun RecipeListTitle(recipe: Recipe) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = recipe.name,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Tags: ${recipe.collectionTags}",
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Edit") }
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Delete") }
        }
    }
}