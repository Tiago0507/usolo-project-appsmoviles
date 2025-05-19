package com.example.usolo.features.products.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun EditTextField(
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = label, fontWeight = FontWeight.Normal)
        OutlinedTextField(
            value = "",
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
