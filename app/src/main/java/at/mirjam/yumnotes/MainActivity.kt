package at.mirjam.yumnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import at.mirjam.yumnotes.ui.theme.YumNotesTheme
import at.mirjam.yumnotes.viewmodel.RecipeViewModel
import at.mirjam.yumnotes.viewmodel.RecipeViewModelFactory

class MainActivity : ComponentActivity() {
    private val recipeViewModel: RecipeViewModel by viewModels {
        RecipeViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YumNotesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RecipeScreen(
                        recipeViewModel = recipeViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeScreen(recipeViewModel: RecipeViewModel, modifier: Modifier = Modifier) {
    val recipes = recipeViewModel.recipes // Observe this flow in your Composable
    // Display the recipes in the UI here
    // You can use LazyColumn to show a list of recipes
    Text(text = "Recipes go here", modifier = modifier)
}
