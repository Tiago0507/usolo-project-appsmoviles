package com.example.usolo.features.postobject.ui.screens

import Header
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.usolo.features.postobject.data.model.RentProduct
import com.example.usolo.features.postobject.ui.components.CategorySection
import com.example.usolo.features.postobject.ui.components.ImageCarousel
import com.example.usolo.features.postobject.ui.components.InputSection
import com.example.usolo.features.postobject.ui.components.PrimaryButton
import com.example.usolo.features.postobject.ui.theme.BackgroundColor
import com.example.usolo.features.postobject.ui.viewmodel.RentProductViewModel

@Composable
fun PublicObjet( loginController: NavController) {
    val context = LocalContext.current
    val resolver = context.contentResolver
    val viewModel = remember { RentProductViewModel() }

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Electrónica") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val publishState by viewModel.publishState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp)) // espacio inicial adicional

            Header(loginController = loginController)

            Spacer(modifier = Modifier.height(5.dp)) // espacio inicial adicional

            InputSection(
                title = "Título",
                value = titulo,
                onValueChange = { titulo = it },
                placeholder = "Ej: Bicicleta de montaña",
                maxChars = 50
            )

            InputSection(
                title = "Descripción",
                value = descripcion,
                onValueChange = { descripcion = it },
                placeholder = "Ej: Ideal para rutas largas, resistente, cómoda",
                maxChars = 200
            )

            CategorySection(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            InputSection(
                title = "Precio por día",
                value = precio,
                onValueChange = { precio = it.filter { c -> c.isDigit() } },
                placeholder = "15000",
                maxChars = 10
            )

            ImageCarousel(onImageSelected = { uri -> selectedImageUri = uri })

            PrimaryButton("Publicar") { 
                val producto = RentProduct(
                    title = titulo,
                    description = descripcion,
                    price_per_day = precio.toDoubleOrNull() ?: 0.0,
                    category = selectedCategory
                )

                viewModel.publishWithImage(producto, resolver, selectedImageUri)

                publishState?.let {
                    if (it.isSuccess) {
                        loginController.popBackStack()
                    } else {
                        Log.e("PublicObjet", "Error: ${it.exceptionOrNull()?.message}")
                    }
                }


            }
        }
    }
}
