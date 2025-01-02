package at.mirjam.yumnotes.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.mirjam.yumnotes.data.Profile
import at.mirjam.yumnotes.data.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class ProfileViewModel(
    private val repository: ProfileRepository,
    private val context: Context
) : ViewModel() {
    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                repository.getProfile().collect { userProfile ->
                    if (userProfile == null) {
                        val defaultProfile = Profile(username = "YumUser")
                        repository.insertProfile(defaultProfile, context)
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

    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            val updatedProfile = profile.value?.copy(username = newUsername) ?: Profile(username = newUsername)
            Log.d("ProfileViewModel", "Updating username to: $newUsername")
            repository.updateProfile(updatedProfile)
            loadProfile() // Ensure the profile is refreshed
        }
    }

    fun updateProfileImageUri(newUri: String) {
        viewModelScope.launch {
            _profile.value?.let { currentProfile ->
                val updatedProfile = currentProfile.copy(profileImageUri = newUri)
                repository.updateProfile(updatedProfile)
                loadProfile()
            }
        }
    }
}