package at.mirjam.yumnotes.content

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.mirjam.yumnotes.R
import at.mirjam.yumnotes.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    val context = LocalContext.current
    val profileState by profileViewModel.profile.collectAsState()
    val profile = profileState

    var username by remember { mutableStateOf(profile?.username ?: "YumUser") }
    var profileImageBitmap by remember {
        mutableStateOf(
            profile?.profileImageUri?.let { uri ->
                try {
                    val inputStream = context.openFileInput(uri)
                    BitmapFactory.decodeStream(inputStream)
                } catch (e: Exception) {
                    null // If the file doesn't exist, fallback to null
                }
            }
        )
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            profileViewModel.saveProfileImage(context, uri) // Save image using repository
            profileImageBitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(it))
        }
    }

    LaunchedEffect(profile?.username, profile?.profileImageUri) {
        username = profile?.username ?: "YumUser"
        profile?.profileImageUri?.let { uri ->
            try {
                val inputStream = context.openFileInput(uri)
                profileImageBitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                profileImageBitmap = null
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp)
        ) {
            if (profileImageBitmap != null) {
                Image(
                    bitmap = profileImageBitmap!!.asImageBitmap(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profile_image),
                    contentDescription = "Default Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center
                )
            }
            IconButton(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(60.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile Picture",
                    modifier = Modifier.fillMaxSize().padding(top = 8.dp)
                )
            }
        }

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                profileViewModel.updateUsername(username)
                Toast.makeText(
                    context,
                    "Profile Saved: $username",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save Profile")
        }
    }
}