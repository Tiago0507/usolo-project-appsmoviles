import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.usolo.R
import com.example.usolo.features.auth.ui.viewmodel.AuthViewModel

@Composable
fun SettingsScreen(loginController: NavHostController) {
    val gradientColors = listOf(
        Color(0xFFF83000),
        Color(0xFFFF6600)
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = gradientColors)
            ),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                OptionItem(iconResId = R.drawable.ic_profile, title = "Perfil")
                OptionItem(iconResId = R.drawable.ic_orders, title = "Órdenes")
                OptionItem(iconResId = R.drawable.ic_offers, title = "Ofertas")
                OptionItem(iconResId = R.drawable.ic_privacy_policy, title = "Política de privacidad")
                OptionItem(iconResId = R.drawable.ic_security, title = "Seguridad")
                Spacer(modifier = Modifier.height(400.dp))
                LogoutButton(loginController = loginController)
            }
        }
    }
}


@Composable
fun OptionItem(iconResId: Int, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Acción de la opción */ }
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Composable
fun LogoutButton(loginController: NavHostController, viewModel: AuthViewModel = viewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable {
                viewModel.logout()
                loginController.navigate("landing") {
                    popUpTo("settings") { inclusive = true }
                    popUpTo("menu") { inclusive = true }
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logout),
            contentDescription = "Cerrar sesión",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Cerrar sesión",
            color = Color(0xFFF83000),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}
