package at.mirjam.yumnotes.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.mirjam.yumnotes.data.Profile
import at.mirjam.yumnotes.data.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val context: Context
) : ViewModel() {
    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    init {
        loadProfile()
    }
    // Load the profile from the repository
    private fun loadProfile() {
        viewModelScope.launch {
            try {
                profileRepository.getProfile().collect { userProfile ->
                    if (userProfile == null) {
                        val defaultProfile = Profile(username = "YumUser")
                        profileRepository.insertProfile(defaultProfile, context)
                        _profile.value = defaultProfile
                    } else {
                        _profile.value = userProfile
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error loading profile: ${e.message}")
            }
        }
    }

    // Update the username in the repository
    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            val updatedProfile = profile.value?.copy(username = newUsername) ?: Profile(username = newUsername)
            Log.d("ProfileViewModel", "Updating username to: $newUsername")
            profileRepository.updateProfile(updatedProfile)
            loadProfile() // Ensure the profile is refreshed
        }
    }

    // Update the profile image URI in the repository
    fun saveProfileImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            val profile = profileRepository.getProfile().firstOrNull()
            profile?.let {
                val imagePath = profileRepository.saveImageToInternalStorage(context, uri)
                profileRepository.updateProfile(it.copy(profileImageUri = imagePath))
            }
        }
    }
}