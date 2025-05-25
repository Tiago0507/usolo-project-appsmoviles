package com.example.usolo.features.postobject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import com.example.usolo.features.postobject.ui.theme.*

@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val backgroundColor = if (isSelected) PrimaryColor else InactiveCategoryColor
    val textColor = if (isSelected) Color.White else TextColor

    Box(
        modifier = Modifier
            .clip(ShapeMedium)
            .background(backgroundColor)
            .clickable(onClick = onSelect)
            .padding(8.dp)
    ) {
        Text(text, color = textColor, style = TextMediumStyle)
    }
}
