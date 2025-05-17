package com.example.usolo.features.registration.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.registration.data.dto.SignUpRequestDTO
import com.example.usolo.features.registration.data.dto.UserWithProfileDTO
import com.example.usolo.features.registration.data.repositories.SignUpRepository
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val userRepository = SignUpRepository()

    private val _signUpState = MutableLiveData<SignUpState>(SignUpState.Idle)
    val signUpState: LiveData<SignUpState> = _signUpState

    fun signUp(
        firstName: String,
        email: String,
        password: String,
        address: String,
        profilePhoto: String? = null
    ) {
        _signUpState.value = SignUpState.Loading

        viewModelScope.launch {
            val signUpRequest = SignUpRequestDTO(
                first_name = firstName,
                email = email,
                password = password,
                address = address,
                profile_photo = profilePhoto
            )

            try {
                val result = userRepository.signUp(signUpRequest)

                result.fold(
                    onSuccess = { _signUpState.value = SignUpState.Success(it) },
                    onFailure = { _signUpState.value = SignUpState.Error(it.message ?: "Error desconocido") }
                )
            } catch (e: Exception) {
                _signUpState.value = SignUpState.Error(e.message ?: "Error inesperado")
            }
        }
    }

    // Estados para SignUp
    sealed class SignUpState {
        object Idle : SignUpState()
        object Loading : SignUpState()
        data class Success(val user: UserWithProfileDTO) : SignUpState()
        data class Error(val message: String) : SignUpState()
    }
}