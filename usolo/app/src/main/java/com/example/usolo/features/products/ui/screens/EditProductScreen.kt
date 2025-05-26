package com.example.usolo.features.products.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.usolo.features.auth.ui.viewmodel.AuthViewModel
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import com.example.usolo.features.products.ui.components.ActionButtons
import com.example.usolo.features.products.ui.components.CategoryDropdown
import com.example.usolo.features.products.ui.components.ProductInputFields
import com.example.usolo.features.products.ui.components.ProductPreviewCard
import com.example.usolo.features.products.ui.components.StatusDropdown
import com.example.usolo.features.products.ui.components.TopBarSection
import com.example.usolo.features.products.ui.viewmodel.EditProductViewModel



@Composable
fun EditProductScreen(
    navController: NavController,
    viewModel: EditProductViewModel = viewModel(),
    productId: Int,
    authViewModel: AuthViewModel = viewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val selectedProduct by viewModel.selectedProduct.collectAsState()
    val statusOptions by viewModel.statuses.collectAsState()
    val categoryOptions by viewModel.categories.collectAsState()

    var newName by remember { mutableStateOf("") }
    var newPrice by remember { mutableStateOf("") }
    var newCategory by remember { mutableStateOf("") }
    var newStatus by remember { mutableStateOf("En buen estado") }

    LaunchedEffect(Unit) {
        viewModel.loadStatuses()
        viewModel.loadCategories()
        viewModel.loadProductById(productId)
        authViewModel.getAuthStatus()
    }

    LaunchedEffect(selectedProduct) {
        selectedProduct?.let {
            newName = it.title
            newPrice = it.price_per_day.toString()
            newCategory = it.category_id.toString()
            newStatus = viewModel.getStatusNameById(it.status_id)
        }
    }

    when (authState.state) {
        com.example.usolo.features.auth.ui.viewmodel.NO_AUTH_STATE -> {
            LaunchedEffect(Unit) {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }

        com.example.usolo.features.auth.ui.viewmodel.IDLE_AUTH_STATE -> {
            androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxSize()) {
                androidx.compose.material3.CircularProgressIndicator()
            }
        }

        com.example.usolo.features.auth.ui.viewmodel.AUTH_STATE -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                TopBarSection { navController.popBackStack() }

                Spacer(modifier = Modifier.height(24.dp))

                selectedProduct?.let { product ->
                    ProductPreviewCard(product = product)

                    Spacer(modifier = Modifier.height(24.dp))

                    ProductInputFields(
                        newName = newName,
                        onNameChange = { newName = it },
                        newPrice = newPrice,
                        onPriceChange = { newPrice = it },
                    )

                    CategoryDropdown(
                        selectedCategory = newCategory,
                        onCategoryChange = { newCategory = it },
                        options = categoryOptions.map { it.name }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    StatusDropdown(
                        selectedStatus = newStatus,
                        onStatusChange = { newStatus = it },
                        options = statusOptions.map { it.name }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ActionButtons(
                        onSave = {
                            if (newName.isNotBlank() && newPrice.toDoubleOrNull() != null) {
                                viewModel.updateProduct(
                                    product.id,
                                    ProductUpdateDto(
                                        title = newName,
                                        description = product.description,
                                        price_per_day = newPrice.toDouble(),
                                        category_id = viewModel.getCategoryIdByName(newCategory),
                                        status_id = viewModel.getStatusIdByName(newStatus),
                                        photo = product.photo
                                    )
                                )
                            }
                        },
                        onDelete = {
                            viewModel.deleteProduct(product.id)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                when (uiState) {
                    is EditProductViewModel.EditProductState.Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    is EditProductViewModel.EditProductState.Success -> Text("¡Producto actualizado con éxito!", color = Color(0xFF4CAF50))
                    is EditProductViewModel.EditProductState.Error -> Text((uiState as EditProductViewModel.EditProductState.Error).message, color = Color.Red)
                    is EditProductViewModel.EditProductState.Deleted -> LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                    else -> {}
                }
            }
        }
    }
}




