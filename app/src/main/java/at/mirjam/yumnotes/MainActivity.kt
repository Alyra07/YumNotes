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
import at.mirjam.yumnotes.content.AddRecipeScreen
import at.mirjam.yumnotes.content.CategoryScreen
import at.mirjam.yumnotes.content.CollectionsScreen
import at.mirjam.yumnotes.content.HomeScreen
import at.mirjam.yumnotes.content.ProfileScreen
import at.mirjam.yumnotes.content.RecipeDetailsView
import at.mirjam.yumnotes.content.RecipeEditView
import at.mirjam.yumnotes.ui.theme.YumNotesTheme
import at.mirjam.yumnotes.util.BottomNavigationBar
import at.mirjam.yumnotes.viewmodel.RecipeViewModel
import at.mirjam.yumnotes.viewmodel.RecipeViewModelFactory

// I used Jetpack Compose with a single MainActivity
// -> managing navigation through a NavHost
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
                    // NAVIGATION
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // ROUTES
                        // HomeScreen
                        composable("home") {
                            HomeScreen(
                                recipeViewModel = recipeViewModel,
                                onRecipeClick = { selectedRecipe ->
                                    navController.navigate("details/${selectedRecipe.id}")
                                },
                                navController = navController
                            )
                        }
                        // CategoryScreen (for selectedTags with tagIcons)
                        composable("category/{tag}") { backStackEntry ->
                            val tag = backStackEntry.arguments?.getString("tag") ?: ""
                            CategoryScreen(
                                recipeViewModel = recipeViewModel,
                                tag = tag,
                                onRecipeClick = { selectedRecipe ->
                                    navController.navigate("details/${selectedRecipe.id}")
                                }
                            )
                        }
                        // AddRecipeScreen
                        composable("addRecipe") {
                            AddRecipeScreen(recipeViewModel = recipeViewModel)
                        }
                        // CollectionsScreen (custom collectionTags)
                        composable("collections") {
                            CollectionsScreen(
                                recipeViewModel = recipeViewModel,
                                onRecipeClick = { selectedRecipe ->
                                    navController.navigate("details/${selectedRecipe.id}")
                                },
                                navController = navController
                            )
                        }
                        // ProfileScreen
                        composable("profile") {
                            ProfileScreen(recipeViewModel = recipeViewModel)
                        }
                        // RecipeDetailsView
                        composable(
                            "details/{recipeId}",
                            arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: 0L
                            val recipe = recipeViewModel.recipes.value.firstOrNull { it.id == recipeId }
                            recipe?.let {
                                RecipeDetailsView(
                                    recipe = it,
                                    onDeleteClick = { selectedRecipe ->
                                        recipeViewModel.deleteRecipe(selectedRecipe)
                                        navController.popBackStack() // Return to the previous screen after deletion
                                    },
                                    onSaveEdit = { updatedRecipe ->
                                        recipeViewModel.updateRecipe(updatedRecipe) // Save the edited recipe
                                    }
                                )
                            }
                        }
                        // RecipeEditView (edit or delete in RecipeDetailsView)
                        composable(
                            "editRecipe/{recipeId}",
                            arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: 0L
                            val recipe = recipeViewModel.recipes.value.firstOrNull { it.id == recipeId }
                            recipe?.let {
                                RecipeEditView(
                                    recipe = it,
                                    onSaveClick = { updatedRecipe ->
                                        recipeViewModel.updateRecipe(updatedRecipe)
                                        navController.popBackStack() // Return to the details screen after saving
                                    },
                                    onCancelClick = {
                                        navController.popBackStack() // Return to the details screen without saving
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}