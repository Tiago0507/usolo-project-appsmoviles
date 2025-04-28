package com.example.usolo.features.menu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.usolo.R
import com.example.usolo.features.menu.data.model.User

class UserProfileViewModel : ViewModel() {

    val user = User(
        name = "Daniel Mejía",
        title = "Alquilador estrella",
        profileImageRes = R.drawable.user_profile
    )
}
