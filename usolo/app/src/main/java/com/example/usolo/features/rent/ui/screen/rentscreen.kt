package com.example.usolo.features.rent.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.usolo.features.rent.ui.viewmodel.NuevaPantallaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentPaymentScreen(
    viewModel: NuevaPantallaViewModel = NuevaPantallaViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Informacion personal",
                        color = Color(0xFFFF5722),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,

                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Información personal",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )


            Text(
                text="Nombre",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text="Apellido",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text="Correo Electronico",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("correo@ejemplo.com") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text="Numero Telefonico",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Numero Telefonico") },
                modifier = Modifier.fillMaxWidth()
            )
            // Selector de método de entrega

            Text(
                text = "Método de entrega",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )


            val opcionesEntrega = listOf("Recoger en algún lugar", "Recibir en la puerta")

            opcionesEntrega.forEach { opcion ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(
                            if (uiState.metodoEntrega == opcion) Color(0xFFE3F2FD) else Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.metodoEntrega == opcion,
                        onClick = { viewModel.onMetodoEntregaChange(opcion) },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1E88E5))
                    )
                    Text(
                        text = opcion,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Text(
                text = "Tiempo de arriendamiento",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var selectedNumber by remember { mutableStateOf("1") }
                var expandedNumber by remember { mutableStateOf(false) }

                Box {
                    OutlinedTextField(
                        value = selectedNumber,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.width(190.dp),
                        label = { Text("Número") },
                        trailingIcon = {
                            IconButton(onClick = { expandedNumber = !expandedNumber }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowRight, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expandedNumber,
                        onDismissRequest = { expandedNumber = false }
                    ) {
                        (1..31).forEach { num ->
                            DropdownMenuItem(
                                text = { Text(num.toString()) },
                                onClick = {
                                    selectedNumber = num.toString()
                                    expandedNumber = false
                                }
                            )
                        }
                    }
                }

                var selectedUnit by remember { mutableStateOf("Días") }
                var expandedUnit by remember { mutableStateOf(false) }

                Box {
                    OutlinedTextField(
                        value = selectedUnit,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.width(190.dp),
                        label = { Text("Unidad") },
                        trailingIcon = {
                            IconButton(onClick = { expandedUnit = !expandedUnit }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowRight, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expandedUnit,
                        onDismissRequest = { expandedUnit = false }
                    ) {
                        listOf("Días", "Meses").forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(unit) },
                                onClick = {
                                    selectedUnit = unit
                                    expandedUnit = false
                                }
                            )
                        }
                    }
                }
            }
            var selectedUnit by remember { mutableStateOf("Efectivo") }
            var expandedUnit by remember { mutableStateOf(false) }
            Box {
                OutlinedTextField(
                    value = selectedUnit,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),

                    label = { Text("Metodo de Pago") },
                    trailingIcon = {
                        IconButton(onClick = { expandedUnit = !expandedUnit }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowRight, contentDescription = null)
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedUnit,
                    onDismissRequest = { expandedUnit = false }
                ) {
                    listOf("Efectivo", "Tarjeta").forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedUnit = unit
                                expandedUnit = false
                            }
                        )
                    }
                }
            }








            Button(
                onClick = { /* TODO: Implementar acción de pago y renta */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                ) {
                Text("Pasar a Pagar", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RentPaymentScreenPreview() {
    RentPaymentScreen()
}
