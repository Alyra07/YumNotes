package at.mirjam.yumnotes.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text

@Composable
fun SearchBar(
    searchQuery: MutableState<String>,
    onSearchQueryChanged: (String) -> Unit,
    placeholderText: String = "Search..."
) {
    TextField(
        value = searchQuery.value,
        onValueChange = { newValue ->
            searchQuery.value = newValue
            onSearchQueryChanged(newValue)
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        },
        leadingIcon = {
            IconButton(onClick = { /* Add search icon behavior here */ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            Color.Transparent, // No underline when focused
            Color.Transparent, // No underline when unfocused
            MaterialTheme.colorScheme.primaryContainer // background color
        ),
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White), // input text white
        singleLine = true
    )
}