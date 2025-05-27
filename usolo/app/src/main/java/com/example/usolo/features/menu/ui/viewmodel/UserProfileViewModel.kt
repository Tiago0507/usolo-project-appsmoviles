package com.example.usolo.features.menu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.R
import com.example.usolo.features.menu.data.model.User
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val userName = LocalDataSourceProvider.get().load("username").firstOrNull()
            val name = userName ?: "Invitado"

            _user.value = User(
                name = name,
                title = "Alquilador estrella",
                profileImageRes = R.drawable.user_profile
            )
        }
    }
}
