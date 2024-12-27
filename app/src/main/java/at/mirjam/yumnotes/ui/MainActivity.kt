package at.mirjam.yumnotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.mirjam.yumnotes.data.RecipeDatabase
import at.mirjam.yumnotes.data.RecipeRepository
import at.mirjam.yumnotes.ui.theme.YumNotesTheme
import at.mirjam.yumnotes.viewmodel.YumNotesViewModel
import at.mirjam.yumnotes.viewmodel.YumNotesViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = RecipeDatabase.getDatabase(this)
        val repository = RecipeRepository(database.recipeDao())
        val viewModel: YumNotesViewModel by viewModels {
            YumNotesViewModelFactory(repository)
        }

        setContent {
            YumNotesTheme {
                AppContent(viewModel)
            }
        }
    }
}

@Composable
fun AppContent(viewModel: YumNotesViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = screens,
                onItemSelected = { selectedItem ->
                    val tab = YumNotesViewModel.Tab.valueOf(selectedItem.label.uppercase())
                    viewModel.selectTab(tab)
                    navController.navigate(selectedItem.label.lowercase())
                },
                selectedTab = viewModel.selectedTab
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            Modifier.padding(padding)
        ) {
            composable("home") { HomeScreen() }
            composable("list") { ListScreen() }
            composable("add recipe") { AddRecipeScreen() }
            composable("collections") { CollectionsScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}
