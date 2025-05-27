package com.example.usolo.features.postobject.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em

val PrimaryColor = Color(0xFFFA4A0C)
val DisabledColor = PrimaryColor.copy(alpha = 0.72f)
val TextColor = Color.Black
val HintColor = TextColor.copy(alpha = 0.5f)
val BackgroundColor = Color.White
val InactiveCategoryColor = Color.Black.copy(alpha = 0.05f)

val ShapeMedium = RoundedCornerShape(6.dp)
val ShapeLarge = RoundedCornerShape(50.dp)
val ShapeFull = RoundedCornerShape(100.dp)

val TextMediumStyle = TextStyle(fontSize = 14.sp, lineHeight = 1.43.em, fontWeight = FontWeight.Medium)
val TextSmallStyle = TextStyle(fontSize = 12.sp, lineHeight = 1.33.em)
