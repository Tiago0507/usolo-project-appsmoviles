package com.example.usolo.features.postobject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.usolo.features.postobject.ui.theme.*

@Composable
fun PrimaryButton(text: String, onPress: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ShapeFull)
            .background(PrimaryColor)
            .clickable(onClick = onPress)
            .padding(12.dp, 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium))
    }
}
