package com.example.usolo.features.postobject.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import com.example.usolo.features.postobject.ui.theme.*


@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextMediumStyle.copy(color = TextColor),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, HintColor.copy(alpha = 0.1f), ShapeMedium)
                    .padding(12.dp, 8.dp)
            ) {
                if (value.isEmpty()) {
                    Text(placeholder, color = HintColor, style = TextMediumStyle)
                }
                innerTextField()
            }
        }
    )
}
