package com.example.usolo.features.auth.ui.viewmodel

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.auth.data.dto.LoginData

import com.example.usolo.features.auth.data.repository.AuthRepository
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class AuthViewModel(
    val authRepository: AuthRepository = AuthRepository()
): ViewModel() {

    var authState: MutableStateFlow<AuthState> = MutableStateFlow<AuthState>(AuthState())
    var userId = MutableStateFlow("")


    fun login(email:String, password:String) {
        viewModelScope.launch(Dispatchers.IO) {
            authState.value = AuthState(state = LOADING_AUTH_STATE)
            val result = authRepository.login(LoginData(email, password))
            if (result.isSuccess) {
                val userId = LocalDataSourceProvider.get().getProfileId().firstOrNull() ?: ""
                authState.value = AuthState(
                    state = AUTH_STATE,
                    userId = userId
                )
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
                LocalDataSourceProvider.get().dataStore.edit { prefs ->
                    prefs.remove(stringPreferencesKey("accesstoken"))
                }
                authState.value = AuthState(state = NO_AUTH_STATE)
            }
        }
    }
}

data class AuthState(
    var state: String = IDLE_AUTH_STATE,
    var errorMessage: String? = null,
    var userId: String? = null
)

var AUTH_STATE = "AUTH"
var NO_AUTH_STATE = "NO_AUTH"
var IDLE_AUTH_STATE = "IDLE_AUTH"
var ERROR_AUTH_STATE = "ERROR_AUTH"
val LOADING_AUTH_STATE = "LOADING_AUTH"