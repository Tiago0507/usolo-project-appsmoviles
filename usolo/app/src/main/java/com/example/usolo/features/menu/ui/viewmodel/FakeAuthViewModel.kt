package com.example.usolo.features.menu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.usolo.features.auth.ui.viewmodel.AUTH_STATE
import com.example.usolo.features.auth.ui.viewmodel.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeAuthViewModel : ViewModel() {


    private val _authState = MutableStateFlow(AuthState(AUTH_STATE))
    val authState: StateFlow<AuthState> = _authState

    fun getAuthStatus() {
    }
}
