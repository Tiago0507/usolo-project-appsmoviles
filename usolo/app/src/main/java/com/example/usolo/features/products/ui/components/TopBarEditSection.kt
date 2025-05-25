package com.example.usolo.features.products.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBarSection(onBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        
        Spacer(modifier = Modifier.width(8.dp))
        Text("Editar producto", style = MaterialTheme.typography.headlineSmall)
    }
}