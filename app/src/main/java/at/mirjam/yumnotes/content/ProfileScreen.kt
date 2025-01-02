package at.mirjam.yumnotes.content

import android.graphics.BitmapFactory
import android.net.Uri
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
    val profileState = profileViewModel.profile.collectAsState()
    val profile = profileState.value

    var username by remember { mutableStateOf(profile?.username ?: "YumUser") }
    var profileImageBitmap by remember {
        mutableStateOf(
            profile?.profileImageUri?.let { uri ->
                BitmapFactory.decodeStream(context.contentResolver.openInputStream(Uri.parse(uri)))
            }
        )
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            profileImageBitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(it))
            profileViewModel.updateProfileImageUri(it.toString())
        }
    }

    LaunchedEffect(profile?.username) {
        // Sync the local state with the ViewModel when profile updates
        username = profile?.username ?: "YumUser"
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
                .size(120.dp)
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
                    .size(32.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile Picture")
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