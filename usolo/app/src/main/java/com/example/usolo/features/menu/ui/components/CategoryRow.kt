package com.example.usolo.features.menu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.graphicsLayer
import com.example.usolo.R

@Composable
fun CategoryRow() {
    val categories = listOf("Electrónicos", "Audiovisuales", "Deportivos", "Juegos")
    val categoryIcons = listOf(
        R.drawable.ic_electronicos,
        R.drawable.ic_audiovisuales,
        R.drawable.ic_deportivos,
        R.drawable.ic_juegos
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories.zip(categoryIcons)) { (category, icon) ->
            CategoryItem(category = category, icon = icon)
        }
    }
}

@Composable
fun CategoryItem(category: String, icon: Int) {
    Card(
        modifier = Modifier
            .size(width = 100.dp, height = 100.dp)
            .graphicsLayer {
                shadowElevation = 4.dp.toPx()
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            },
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = icon),
                    contentDescription = category,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterHorizontally)
                )


                Text(
                    text = category,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black

                )
            }
        }
    }
}
