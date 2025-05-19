package com.example.usolo.features.products.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductImageCard(productName: String, imageUrl: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Text(
                text = "Etiqueta",
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                fontWeight = FontWeight.Light
            )

            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = productName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Nombre producto",
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = productName,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp,)
            )
        }
    }
}
