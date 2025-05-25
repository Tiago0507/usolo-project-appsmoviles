package com.example.usolo.features.products.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ProductInputFields(
    newName: String,
    onNameChange: (String) -> Unit,
    newPrice: String,
    onPriceChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = newName,
        onValueChange = onNameChange,
        label = { Text("Nuevo nombre") },
        placeholder = { Text("Ingrese el nuevo nombre del producto") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = newPrice,
        onValueChange = onPriceChange,
        label = { Text("Nuevo precio") },
        placeholder = { Text("Ingrese el nuevo precio del producto") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(12.dp))
}
