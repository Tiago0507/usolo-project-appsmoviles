package com.example.usolo.features.menu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usolo.features.menu.ui.viewmodel.UserProfileViewModel

@Composable
fun UserProfileSection(viewModel: UserProfileViewModel = viewModel()) {
    val user = viewModel.user

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = user.profileImageRes),
            contentDescription = "Perfil de Usuario",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = user.name)
            Text(text = user.title)
        }
    }
}
