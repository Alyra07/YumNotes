package at.mirjam.yumnotes.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.mirjam.yumnotes.data.Profile
import at.mirjam.yumnotes.data.ProfileRepository
import at.mirjam.yumnotes.util.FileUtil
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

    // Update profile information
    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.updateProfile(profile)
            _profile.value = profile // update the local state as well
        }
    }

    // Update the profile image URI in the repository
    fun saveProfileImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            val profile = profileRepository.getProfile().firstOrNull()
            profile?.let {
                // save the image and get the path
                val imagePath = FileUtil.saveImageToInternalStorage(context, uri)
                // update profile with the new image path
                profileRepository.updateProfile(it.copy(profileImageUri = imagePath))
            }
        }
    }

}