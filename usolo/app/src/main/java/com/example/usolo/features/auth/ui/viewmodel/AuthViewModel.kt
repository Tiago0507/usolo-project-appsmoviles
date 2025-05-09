package com.example.usolo.features.auth.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usolo.features.auth.data.dto.LoginData

import com.example.usolo.features.auth.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    val authRepository: AuthRepository = AuthRepository()
): ViewModel() {

    var authState: MutableStateFlow<AuthState> = MutableStateFlow<AuthState>(AuthState())

    fun login(email:String, password:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.login(LoginData(email, password))
            if (result.isSuccess) {
                authState.value = AuthState(state = AUTH_STATE)
            } else {
                authState.value = AuthState(state = ERROR_AUTH_STATE, errorMessage = result.exceptionOrNull()?.message)
            }
        }
    }

    fun getAuthStatus() {
        viewModelScope.launch (Dispatchers.IO){
            var accessToken = authRepository.getAccessToken()
            accessToken?.let {
                if(it.isEmpty()){
                    authState.value = AuthState(state = NO_AUTH_STATE)
                }else{
                    authState.value = AuthState(state = AUTH_STATE)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            val success = authRepository.logout()
            if (success) {
                authState.value = AuthState(state = NO_AUTH_STATE)
            }
        }
    }
}

data class AuthState(
    var state: String = IDLE_AUTH_STATE,
    var errorMessage: String? = null
)

var AUTH_STATE = "AUTH"
var NO_AUTH_STATE = "NO_AUTH"
var IDLE_AUTH_STATE = "IDLE_AUTH"
var ERROR_AUTH_STATE = "ERROR_AUTH"