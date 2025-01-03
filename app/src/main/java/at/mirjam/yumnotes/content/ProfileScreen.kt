package at.mirjam.yumnotes.content

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import at.mirjam.yumnotes.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    val context = LocalContext.current
    val profileState by profileViewModel.profile.collectAsState()
    val profile = profileState

    var username by remember { mutableStateOf(profile?.username ?: "YumUser") }
    var profileImageUri by remember { mutableStateOf(profile?.profileImageUri) }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            profileViewModel.saveProfileImage(context, uri)
            profileImageUri = uri.toString()  // Save the URI for future use
        }
    }

    LaunchedEffect(profile?.username, profile?.profileImageUri) {
        username = profile?.username ?: "YumUser"
        profileImageUri = profile?.profileImageUri
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { // Profile Image
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
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(
                    onClick = {
                        profileImageUri = null
                        profileViewModel.updateProfile(profile?.copy(profileImageUri = null) ?: return@IconButton)
                    },
                    modifier = Modifier.size(60.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Profile Picture",
                    )
                }
            }
        }
        item { // Username field
            TextField(
                value = username,
                onValueChange = { newText ->
                    if (newText.length in 1..30) { // Username length
                        username = newText
                    } else if (newText.length > 30) {
                        // show an error message or truncate the text to 30 characters
                        username = newText.take(30)
                    }
                },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        item { // Save button
            Button(
                onClick = {
                    profileViewModel.updateProfile(profile?.copy(username = username) ?: return@Button)
                    Toast.makeText(context, "Username saved: $username", Toast.LENGTH_SHORT).show()
                },
            ) {
                Text("Save Username")
            }
        }
    }
}