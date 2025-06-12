package com.example.usolo.features.payment_gateway.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.usolo.features.payment_gateway.models.PaymentMethod

@Composable
fun PaymentMethodCard(
    method: PaymentMethod,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        2.dp,
                        Color(0xFFFF5722),
                        RoundedCornerShape(8.dp)
                    )
                } else {
                    Modifier
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelected,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFFFF5722)
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = method.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = method.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}