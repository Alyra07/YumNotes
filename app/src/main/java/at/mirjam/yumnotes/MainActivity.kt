package at.mirjam.yumnotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.mirjam.yumnotes.components.AddRecipeScreen
import at.mirjam.yumnotes.components.CollectionsScreen
import at.mirjam.yumnotes.components.HomeScreen
import at.mirjam.yumnotes.components.ProfileScreen
import at.mirjam.yumnotes.components.RecipeDetailsView
import at.mirjam.yumnotes.ui.theme.YumNotesTheme
import at.mirjam.yumnotes.viewmodel.RecipeViewModel
import at.mirjam.yumnotes.viewmodel.RecipeViewModelFactory

class MainActivity : ComponentActivity() {
    private val recipeViewModel: RecipeViewModel by viewModels {
        RecipeViewModelFactory(applicationContext)
    }

    @SuppressLint("StateFlowValueCalledInComposition")
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
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                recipeViewModel = recipeViewModel,
                                onRecipeClick = { selectedRecipe ->
                                    navController.navigate("details/${selectedRecipe.id}")
                                }
                            )
                        }
                        composable("addRecipe") {
                            AddRecipeScreen(recipeViewModel = recipeViewModel)
                        }
                        composable("collections") {
                            CollectionsScreen(
                                recipeViewModel = recipeViewModel,
                                onRecipeClick = { selectedRecipe ->
                                    navController.navigate("details/${selectedRecipe.id}")
                                }
                            )
                        }
                        composable(
                            "details/{recipeId}",
                            arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: 0L
                            val recipe =
                                recipeViewModel.recipes.value.firstOrNull { it.id == recipeId }
                            recipe?.let {
                                RecipeDetailsView(it)
                            }
                        }
                        composable("profile") {
                            ProfileScreen(recipeViewModel = recipeViewModel)
                        }
                    }
                }
            }
        }
    }
}
