package com.example.usolo.features.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeAuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow(AuthState(AUTH_STATE))
    val authState: StateFlow<AuthState> = _authState

    fun getAuthStatus() {
    }
}
