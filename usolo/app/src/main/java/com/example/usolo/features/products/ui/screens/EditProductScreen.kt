package com.example.usolo.features.products.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usolo.features.products.ui.components.EditTextField
import com.example.usolo.features.products.ui.components.ProductImageCard
import com.example.usolo.features.products.ui.components.StateDropdown
import com.example.usolo.features.products.ui.viewmodel.EditProductViewModel


@Composable
fun EditProductScreen(
    productId: String,
    productName: String,
    imageUrl: String,
    viewModel: EditProductViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Editar producto", color = Color(0xFFFF5722), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Producto a editar", fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(8.dp))

        ProductImageCard(productName = productName, imageUrl = imageUrl)

        Spacer(modifier = Modifier.height(24.dp))

        EditTextField(
            label = "Nuevo nombre",
            placeholder = "Ingrese el nuevo nombre del producto",
            onValueChange = viewModel::onNameChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        EditTextField(
            label = "Nuevo precio",
            placeholder = "Ingrese el nuevo precio del producto",
            onValueChange = viewModel::onPriceChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        EditTextField(
            label = "Nueva categoría",
            placeholder = "Ingrese la nueva categoría del producto",
            onValueChange = viewModel::onCategoryChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        StateDropdown(
            selectedState = viewModel.state,
            onStateSelected = viewModel::onStateChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.saveProductChanges(productId)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }
    }
}
