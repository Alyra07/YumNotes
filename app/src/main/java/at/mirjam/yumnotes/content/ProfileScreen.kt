package at.mirjam.yumnotes.content

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.io.File
import at.mirjam.yumnotes.R
import at.mirjam.yumnotes.util.HeaderWithLogo
import at.mirjam.yumnotes.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    val context = LocalContext.current
    val profileState by profileViewModel.profile.collectAsState()
    val profile = profileState

    // Initialize username and profile image URI
    var username by remember { mutableStateOf(profile?.username ?: "YumUser") }
    var profileImageUri by remember { mutableStateOf(profile?.profileImageUri) }

    LaunchedEffect(profile?.username, profile?.profileImageUri) {
        username = profile?.username ?: "YumUser" // Default username YumUser
        profileImageUri = profile?.profileImageUri // Update the URI
    }

    // Handle profile image selection
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                profileViewModel.saveProfileImage(context, uri)
                profileImageUri = uri.toString()  // Save the URI for future use
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // HEADING with Logo
        HeaderWithLogo(heading = "Your Profile")

        // PROFILE IMAGE
        Box(
            modifier = Modifier
                .size(250.dp)
                .padding(8.dp)
        ) {
            profileImageUri?.let {
                Image( // Display the selected profile image
                    painter = rememberAsyncImagePainter(File(context.filesDir, it)),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center
                )
            } ?: run {
                // Default image when no profile image is selected
                Image(
                    painter = painterResource(id = R.drawable.profile_image),
                    contentDescription = "Default Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center
                )
            }
        }

        // Edit and delete buttons for Profile image
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            IconButton(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.size(60.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile Picture",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(
                onClick = {
                    profileImageUri = null
                    profileViewModel.updateProfile(
                        profile?.copy(profileImageUri = null) ?: return@IconButton
                    )
                },
                modifier = Modifier.size(60.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Profile Picture",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }

        // Username field (can edit)
        OutlinedTextField(
            value = username,
            onValueChange = { newText ->
                if (newText.length in 1..30) {
                    username = newText
                } else if (newText.length > 30) {
                    username = newText.take(30)
                }
            },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors( // used to customize colors of the TextField
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.onSurface,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )

        // Save Username button
        Button(
            onClick = {
                // update new username in ProfileRepository
                profileViewModel.updateProfile(profile?.copy(username = username) ?: return@Button)
                Toast.makeText(context, "Username saved: $username", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Username")
        }
    }
}