package com.example.usolo.features.postobject.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.example.usolo.features.postobject.ui.theme.*

@Composable
fun CategorySection(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("Hogar", "Electrónica", "Deportes", "Moda", "Otras")

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Categoría", color = TextColor, style = TextMediumStyle)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            categories.forEach { category ->
                CategoryChip(
                    text = category,
                    isSelected = category == selectedCategory,
                    onSelect = { onCategorySelected(category) }
                )
            }
        }

        Text("Seleccione una categoría", color = HintColor, style = TextSmallStyle)
    }
}


