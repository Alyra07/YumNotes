package at.mirjam.yumnotes.data

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

class ProfileRepository(private val profileDao: ProfileDao) {

    suspend fun insertProfile(profile: Profile, context: Context) {
        try {
            profile.profileImageUri?.let {
                val imagePath = saveImageToInternalStorage(context, Uri.parse(it))
                val profileWithImagePath = profile.copy(profileImageUri = imagePath)
                profileDao.insertProfile(profileWithImagePath)
                Log.d("ProfileRepository", "Profile inserted successfully: $profileWithImagePath")
            } ?: run {
                profileDao.insertProfile(profile)
                Log.d("ProfileRepository", "Profile inserted without image: $profile")
            }
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error inserting profile: ${e.message}")
        }
    }

    fun getProfile(): Flow<Profile?> {
        return try {
            Log.d("ProfileRepository", "Fetching profile from database.")
            profileDao.getProfile()
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error fetching profile: ${e.message}")
            flowOf(null)
        }
    }

    suspend fun updateProfile(profile: Profile) {
        try {
            profileDao.updateProfile(profile)
            Log.d("ProfileRepository", "Profile updated successfully: $profile")
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error updating profile: ${e.message}")
        }
    }

    private fun saveImageToInternalStorage(context: Context, uri: Uri): String {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return fileName
    }
}