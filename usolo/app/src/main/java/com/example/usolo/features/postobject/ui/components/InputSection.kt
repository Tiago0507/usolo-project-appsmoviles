package com.example.usolo.features.postobject.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import com.example.usolo.features.postobject.ui.theme.*

@Composable
fun InputSection(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    maxChars: Int,
    helperText: String = "Máximo $maxChars caracteres",
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, color = TextColor, style = TextMediumStyle)

        InputField(
            value = value,
            onValueChange = { if (it.length <= maxChars) onValueChange(it) },
            placeholder = placeholder,
            keyboardType = keyboardType
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(helperText, color = HintColor, style = TextSmallStyle)
            Text("${value.length}/$maxChars",
                color = if (value.length == maxChars) Color.Red else HintColor,
                style = TextSmallStyle)
        }
    }
}
