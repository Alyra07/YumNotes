package at.mirjam.yumnotes.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.mirjam.yumnotes.R

// Tags for category icons (CategoryScreen)
// assigns predefined tags to Icons for categories
val tagIcons = mapOf(
    "Quick" to R.drawable.quick_icon,
    "Italian" to R.drawable.italian_icon,
    "Vegetarian" to R.drawable.vegetarian_icon,
    "Sweet" to R.drawable.sweet_icon,
    "Asian Cuisine" to R.drawable.asiancuisine_icon,
)

// Category Icon Row to display category icons (HomeScreen, CollectionsScreen)
@Composable
fun CategoryIconRow(navController: NavController) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        item {
            // Custom /res/drawable tagIcons
            tagIcons.forEach { (tag, iconRes) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .clickable {
                            // Navigate to CategoryScreen with the selected tag
                            navController.navigate("category/$tag")
                        }
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = "$tag Icon",
                        modifier = Modifier.size(48.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                    Text(
                        text = tag,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}