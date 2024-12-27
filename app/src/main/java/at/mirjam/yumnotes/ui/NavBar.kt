package at.mirjam.yumnotes.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import at.mirjam.yumnotes.viewmodel.YumNotesViewModel
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.Composable

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val content: @Composable () -> Unit
)

val screens = listOf(
    BottomNavItem("Home", Icons.Default.Home) { HomeScreen() },
    BottomNavItem("List", Icons.AutoMirrored.Filled.List) { ListScreen() },
    BottomNavItem("Add Recipe", Icons.Default.Add) { AddRecipeScreen() },
    BottomNavItem("Collections", Icons.Default.Check) { CollectionsScreen() },
    BottomNavItem("Profile", Icons.Default.Person) { ProfileScreen() }
)

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    onItemSelected: (BottomNavItem) -> Unit,
    selectedTab: YumNotesViewModel.Tab
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedTab.name == item.label.uppercase(),
                onClick = { onItemSelected(item) }
            )
        }
    }
}
