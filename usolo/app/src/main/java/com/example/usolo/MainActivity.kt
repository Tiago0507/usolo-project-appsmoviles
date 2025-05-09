package com.example.usolo

import SettingsScreen
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.usolo.features.screens.LandingScreen
import com.example.usolo.features.screens.LoginScreen
import com.example.usolo.features.registration.ui.screens.SignUpScreen
import com.example.usolo.ui.theme.UsoloTheme
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usolo.features.registration.ui.screens.EmailSignUpScreen
import com.example.usolo.features.registration.ui.viewmodel.SignUpViewModel
import com.example.usolo.features.menu.ui.screens.MainMenu


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AppVariables")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalDataSourceProvider.init(applicationContext.dataStore)

        enableEdgeToEdge()
        setContent {
            UsoloTheme {
                App()
                }
            }
        }
    }


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun App() {
    val loginController = rememberNavController()
    val signUpViewModel: SignUpViewModel = viewModel()

    NavHost(navController = loginController, startDestination = "landing") {
        composable(
            "landing",
            enterTransition = { slideInVertically(initialOffsetY = { 1000 }) }, // Entrada desde abajo
            exitTransition = { slideOutVertically(targetOffsetY = { -1000 }) } // Salida hacia arriba
        ) {
            LandingScreen(loginController = loginController)
        }
        composable(
            "login",
            enterTransition = { fadeIn() }, // Desvanecimiento para la entrada
            exitTransition = { fadeOut() } // Desvanecimiento para la salida
        ) {
            LoginScreen(loginController = loginController)
        }
        composable(
            "signup",
            enterTransition = { slideInVertically(initialOffsetY = { 1000 }) }, // Entrada desde abajo
            exitTransition = { slideOutVertically(targetOffsetY = { -1000 }) } // Salida hacia arriba
        ) {
            SignUpScreen(loginController = loginController)
        }
        composable("email_signup"){
            EmailSignUpScreen(navController = loginController, viewModel = signUpViewModel )
        }
        composable(
            "menu",
            enterTransition = { slideInVertically(initialOffsetY = { 1000 }) }, // Entrada desde abajo
            exitTransition = { fadeOut() } // Salida desvanecida para la pantalla anterior
        ) {
            MainMenu(loginController = loginController)
        }
        composable(
            "settings",
            enterTransition = { slideInVertically(initialOffsetY = { 1000 }) }, // Entrada desde abajo
            exitTransition = { fadeOut() } // Salida desvanecida para la pantalla anterior
        ) {
            SettingsScreen(loginController = loginController)
        }

    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val loginController = rememberNavController()
    SignUpScreen(loginController)
}