package at.mirjam.yumnotes.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text

@Composable
fun SearchBar(
    searchQuery: MutableState<String>,
    onSearchQueryChanged: (String) -> Unit
) {
    TextField(
        value = searchQuery.value,
        onValueChange = { newValue ->
            searchQuery.value = newValue
            onSearchQueryChanged(newValue)
        },
        placeholder = {
            Text(
                text = "Search for a recipe...",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        },
        leadingIcon = {
            IconButton(onClick = { /* Add search icon behavior here */ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            Color.Transparent, // No underline when focused
            Color.Transparent, // No underline when unfocused
            MaterialTheme.colorScheme.primaryContainer // background color
        ),
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White), // Set input text to white
        singleLine = true
    )
}