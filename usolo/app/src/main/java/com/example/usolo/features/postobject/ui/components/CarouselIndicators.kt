package com.example.usolo.features.postobject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.usolo.features.postobject.ui.theme.ShapeFull


@Composable
fun CarouselIndicators(count: Int, selectedIndex: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(count) { index ->
            IndicatorDot(isSelected = index == selectedIndex)
        }
    }
}

@Composable
fun IndicatorDot(isSelected: Boolean) {
    val color = if (isSelected) Color.White else Color.Black.copy(alpha = 0.3f)
    val width = if (isSelected) 20.dp else 4.dp

    Box(
        modifier = Modifier
            .size(width, 4.dp)
            .clip(ShapeFull)
            .background(color)
    )
}
