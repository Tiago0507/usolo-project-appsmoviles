package com.example.usolo.features.payment_gateway.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.usolo.features.payment_gateway.models.PaymentMethod
import com.example.usolo.features.payment_gateway.ui.components.PaymentMethodCard
import com.example.usolo.features.payment_gateway.ui.components.PaymentTopBar
import com.example.usolo.features.payment_gateway.ui.components.ProductInfoCard
import com.example.usolo.features.payment_gateway.ui.viewmodel.PaymentViewModel
import com.example.usolo.features.products.data.dto.ProductData
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController,
    product: ProductData,
    viewModel: PaymentViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val paymentMethods by viewModel.paymentMethods.collectAsState()

    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    var totalPrice by remember { mutableDoubleStateOf(0.0) }

    // Observar cambios de estado
    LaunchedEffect(uiState) {
        if (uiState.paymentSuccess) {
            Toast.makeText(context, uiState.successMessage, Toast.LENGTH_LONG).show()
            navController.popBackStack()
        }

        uiState.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    // Calcular precio cuando cambien las fechas
    LaunchedEffect(startDate, endDate) {
        if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
            val days = calculateDaysBetween(startDate, endDate)
            totalPrice = product.price_per_day * days
        }
    }

    Scaffold(
        topBar = {
            PaymentTopBar(
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Información del producto
            ProductInfoCard(product = product)

            // Selección de fechas
            DateSelectionCard(
                startDate = startDate,
                endDate = endDate,
                totalPrice = totalPrice,
                pricePerDay = product.price_per_day,
                onStartDateSelected = { startDate = it },
                onEndDateSelected = { endDate = it }
            )

            // Métodos de pago
            PaymentMethodsCard(
                paymentMethods = paymentMethods,
                selectedMethod = selectedPaymentMethod,
                onMethodSelected = { selectedPaymentMethod = it },
                isLoading = uiState.isLoading
            )

            // Botón de pago
            Button(
                onClick = {
                    if (validateInputs(startDate, endDate, selectedPaymentMethod, context)) {
                        viewModel.processReservationAndPayment(
                            product = product,
                            startDate = startDate,
                            endDate = endDate,
                            selectedPaymentMethod = selectedPaymentMethod!!
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722)
                )
            ) {
                if (uiState.isLoading) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "Procesando...",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Text(
                        text = if (totalPrice > 0) {
                            "Procesar Pago - $${String.format("%.2f", totalPrice)}"
                        } else {
                            "Procesar Pago"
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Espaciado inferior
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun DateSelectionCard(
    startDate: String,
    endDate: String,
    totalPrice: Double,
    pricePerDay: Double,
    onStartDateSelected: (String) -> Unit,
    onEndDateSelected: (String) -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Periodo de reserva",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { showDatePicker(context, true, onStartDateSelected) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFFF5722)
                    )
                ) {
                    Text(
                        text = if (startDate.isEmpty()) "Fecha inicio" else formatDateForDisplay(startDate),
                        fontSize = 14.sp
                    )
                }

                OutlinedButton(
                    onClick = { showDatePicker(context, false, onEndDateSelected) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFFF5722)
                    )
                ) {
                    Text(
                        text = if (endDate.isEmpty()) "Fecha fin" else formatDateForDisplay(endDate),
                        fontSize = 14.sp
                    )
                }
            }

            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                val days = calculateDaysBetween(startDate, endDate)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFF5722).copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Resumen de costos:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFF5722)
                        )
                        Text(
                            text = "$${String.format("%.2f", pricePerDay)} × $days días",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        Divider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = Color(0xFFFF5722).copy(alpha = 0.3f)
                        )
                        Text(
                            text = "Total: $${String.format("%.2f", totalPrice)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF5722)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentMethodsCard(
    paymentMethods: List<PaymentMethod>,
    selectedMethod: PaymentMethod?,
    onMethodSelected: (PaymentMethod) -> Unit,
    isLoading: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Método de pago",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722)
            )

            if (isLoading && paymentMethods.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color(0xFFFF5722)
                        )
                        Text(
                            text = "Cargando métodos de pago...",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else if (paymentMethods.isEmpty()) {
                Text(
                    text = "No hay métodos de pago disponibles",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                paymentMethods.forEach { method ->
                    PaymentMethodCard(
                        method = method,
                        isSelected = selectedMethod?.id == method.id,
                        onSelected = { onMethodSelected(method) }
                    )
                }
            }
        }
    }
}

private fun showDatePicker(
    context: android.content.Context,
    isStartDate: Boolean,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            onDateSelected(format.format(selectedDate.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.minDate = if (isStartDate) {
            System.currentTimeMillis()
        } else {
            System.currentTimeMillis()
        }
    }.show()
}

private fun formatDateForDisplay(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        dateString
    }
}

private fun calculateDaysBetween(startDate: String, endDate: String): Long {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val start = format.parse(startDate)
        val end = format.parse(endDate)
        ((end!!.time - start!!.time) / (1000 * 60 * 60 * 24)) + 1
    } catch (e: Exception) {
        1
    }
}

private fun validateInputs(
    startDate: String,
    endDate: String,
    selectedMethod: PaymentMethod?,
    context: android.content.Context
): Boolean {
    return when {
        startDate.isEmpty() -> {
            Toast.makeText(context, "Selecciona fecha de inicio", Toast.LENGTH_SHORT).show()
            false
        }
        endDate.isEmpty() -> {
            Toast.makeText(context, "Selecciona fecha de fin", Toast.LENGTH_SHORT).show()
            false
        }
        selectedMethod == null -> {
            Toast.makeText(context, "Selecciona método de pago", Toast.LENGTH_SHORT).show()
            false
        }
        else -> {
            // Validar que la fecha de fin no sea anterior a la de inicio
            try {
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val start = format.parse(startDate)
                val end = format.parse(endDate)
                if (end!!.before(start)) {
                    Toast.makeText(context, "La fecha de fin debe ser posterior a la de inicio", Toast.LENGTH_SHORT).show()
                    false
                } else {
                    true
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error en las fechas seleccionadas", Toast.LENGTH_SHORT).show()
                false
            }
        }
    }
}