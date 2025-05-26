package com.example.usolo.features.products.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtons(
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    Button(
        onClick = onSave,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
    ) {
        Text("Guardar cambios")
    }

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedButton(
        onClick = onDelete,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
    ) {
        Text("Eliminar producto")
    }
}
