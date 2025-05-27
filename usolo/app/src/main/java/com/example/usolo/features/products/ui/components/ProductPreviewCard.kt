import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@Composable
fun ProductPreviewCard(product: ProductData) {
    val context = LocalContext.current

    // Obtener el token de manera síncrona (ya que estás en Composable)
    val token = remember {
        runBlocking {
            LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
        }
    }

    val imageRequest = remember(product.photo, token) {
        ImageRequest.Builder(context)
            .data("http://10.0.2.2:8055/assets/${product.photo}")
            .addHeader("Authorization", "Bearer $token")
            .crossfade(true)
            .build()
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Etiqueta", style = MaterialTheme.typography.labelMedium)

            AsyncImage(
                model = imageRequest,
                contentDescription = "Producto",
                modifier = Modifier
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Nombre producto", fontWeight = FontWeight.Bold)
            Text(product.title)
        }
    }
}
