package at.mirjam.yumnotes.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

@Composable
fun SearchBar(
    searchQuery: MutableState<String>,
    onSearchQueryChanged: (String) -> Unit,
    placeholderText: String = "Search..."
) {
    TextField(
        value = searchQuery.value,
        // set the value of the search field
        onValueChange = { newValue ->
            searchQuery.value = newValue
            onSearchQueryChanged(newValue)
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingIcon = { // search icon
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            cursorColor = MaterialTheme.colorScheme.onSurface,
        ),
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
        singleLine = true
    )
}