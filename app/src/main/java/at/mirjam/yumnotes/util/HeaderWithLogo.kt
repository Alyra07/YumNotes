package at.mirjam.yumnotes.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.R

@Composable
fun HeaderWithLogo(
    heading: String,
) {
    // Used for custom headline text (depends on the screen) with logo
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text( // HEADING
            text = heading,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f) // fill space next to the logo
        )
        Image( // BACKGROUND LOGO
            painter = painterResource(id = R.drawable.yumnotes_logo),
            contentDescription = "YumNotes Logo",
            modifier = Modifier.size(68.dp),
            alpha = 0.6f // Subtle transparency
        )
    }
}