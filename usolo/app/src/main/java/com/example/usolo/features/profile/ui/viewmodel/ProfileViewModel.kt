package com.example.usolo.features.profile.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.profile.domain.model.UserProfile
import com.example.usolo.features.profile.data.repository.impl.ProfileRepositoryImpl
import com.example.usolo.features.profile.data.repository.interfaces.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository = ProfileRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val profileId = LocalDataSourceProvider.get().getProfileId().firstOrNull()
                val directusUserId = LocalDataSourceProvider.get().load("directus_user_id").firstOrNull()

                Log.d("ProfileViewModel", "ProfileId: $profileId, DirectusUserId: $directusUserId")

                if (profileId != null) {
                    val profileIdInt = profileId.toIntOrNull() ?: 1
                    val userIdSafe = directusUserId ?: "unknown"

                    val result = repository.getUserProfile(profileIdInt, userIdSafe)

                    result.fold(
                        onSuccess = { userProfile ->
                            _uiState.value = _uiState.value.copy(
                                userProfile = userProfile,
                                isLoading = false,
                                error = null
                            )
                        },
                        onFailure = { error ->
                            Log.e("ProfileViewModel", "Error loading profile: ${error.message}")
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = error.message
                            )
                        }
                    )
                } else {
                    // Crear un perfil por defecto si no hay datos
                    val defaultProfile = UserProfile(
                        id = 1,
                        firstName = "Usuario USOLO",
                        email = "usuario@usolo.com",
                        address = "Dirección no disponible",
                        profilePhoto = null,
                        publishedItemsCount = 0,
                        completedRentalsCount = 0
                    )

                    _uiState.value = _uiState.value.copy(
                        userProfile = defaultProfile,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Exception loading profile", e)

                // Perfil de emergencia para evitar crashes
                val emergencyProfile = UserProfile(
                    id = 1,
                    firstName = "Usuario USOLO",
                    email = "usuario@usolo.com",
                    address = "Dirección no disponible",
                    profilePhoto = null,
                    publishedItemsCount = 0,
                    completedRentalsCount = 0
                )

                _uiState.value = _uiState.value.copy(
                    userProfile = emergencyProfile,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    fun refreshProfile() {
        loadUserProfile()
    }
}

data class ProfileState(
    val userProfile: UserProfile? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)