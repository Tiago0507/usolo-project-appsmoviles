import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.usolo.features.menu.ui.components.UserProfileSection
import com.example.usolo.features.rental_registration.domain.model.RentalItem
import com.example.usolo.features.rental_registration.utils.toDirectusImageUrl

@Composable
fun BaseProductCard(
    rentalItem: RentalItem,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .width(180.dp)  // Reducido para mejor proporción
            .height(260.dp), // Ajustado para mejor distribución
        shape = RoundedCornerShape(16.dp), // Bordes más redondeados como el modelo
        elevation = CardDefaults.cardElevation(8.dp), // Sombra más pronunciada
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = { onClick?.invoke() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp), // Sin padding general para maximizar espacio
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Contenedor de imagen con fondo redondeado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp) // Más espacio para la imagen
                    .padding(12.dp) // Padding solo alrededor de la imagen
            ) {
                val imageUrl = rentalItem.photo.toDirectusImageUrl()
                Log.d(">>> IMAGE URL", "URL generada: $imageUrl")
                AsyncImage(
                    model = imageUrl,
                    contentDescription = rentalItem.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)), // Bordes redondeados en la imagen
                    contentScale = ContentScale.Crop
                )
            }

            // Contenedor del texto con padding específico
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = rentalItem.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp, // Tamaño específico
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 2, // Máximo 2 líneas para el título
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${rentalItem.pricePerDay} por día",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp, // Tamaño más grande para el precio
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Spacer para empujar el contenido hacia arriba
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}