package at.mirjam.yumnotes.data

import android.content.Context
import android.net.Uri
import android.util.Log
import at.mirjam.yumnotes.util.FileUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// ProfileRepository controls access to the ProfileDao (username & profile image)
class ProfileRepository(private val profileDao: ProfileDao) {

    // CREATE
    suspend fun insertProfile(profile: Profile, context: Context) {
        try {
            // Save the profile image to internal storage
            val profileWithImagePath = profile.copy(
                profileImageUri = profile.profileImageUri?.let {
                    FileUtil.saveImageToInternalStorage(context, Uri.parse(it))
                }
            )
            profileDao.insertProfile(profileWithImagePath) // Insert profile with image path
            Log.d("ProfileRepository", "Profile inserted successfully: $profileWithImagePath")
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error inserting profile: ${e.message}")
        }
    }

    // READ
    fun getProfile(): Flow<Profile?> {
        return try {
            Log.d("ProfileRepository", "Fetching profile from database.")
            profileDao.getProfile()
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error fetching profile: ${e.message}")
            flowOf(null)
        }
    }

    // UPDATE
    suspend fun updateProfile(profile: Profile) {
        try {
            profileDao.updateProfile(profile)
            Log.d("ProfileRepository", "Profile updated successfully: $profile")
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error updating profile: ${e.message}")
        }
    }
}