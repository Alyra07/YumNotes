package at.mirjam.yumnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.mirjam.yumnotes.screens.AddRecipeScreen
import at.mirjam.yumnotes.screens.CollectionsScreen
import at.mirjam.yumnotes.screens.HomeScreen
import at.mirjam.yumnotes.screens.ProfileScreen
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
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { innerPadding -> // space for navigation bar
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding) // Apply padding to NavHost
                    ) {
                        composable("home") { HomeScreen(recipeViewModel = recipeViewModel) }
                        composable("addRecipe") { AddRecipeScreen(recipeViewModel = recipeViewModel) }
                        composable("collections") { CollectionsScreen(recipeViewModel = recipeViewModel) }
                        composable("profile") { ProfileScreen(recipeViewModel = recipeViewModel) }
                } }
            }
        }
    }
}
