package at.mirjam.yumnotes.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.mirjam.yumnotes.R

// Tags for predefined category icons - used in AddRecipeScreen
val predefinedTags = listOf("Italian", "Vegetarian", "Quick", "Advanced")

// Icons for categories
val tagIcons = mapOf(
    "Italian" to R.drawable.italian,
    "Vegetarian" to R.drawable.vegetariansalad,
    "Quick" to R.drawable.quicksandwich,
    "Advanced" to R.drawable.advanced
)

@Composable
fun CategoryIconRow(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Custom /res/drawable tagIcons
        tagIcons.forEach { (tag, iconRes) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    // Navigate to CategoryScreen with the selected tag
                    navController.navigate("category/$tag")
                }
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "$tag Icon",
                    modifier = Modifier.size(48.dp)
                )
                Text(text = tag)
            }
        }
    }
}
