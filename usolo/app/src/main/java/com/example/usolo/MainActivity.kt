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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.usolo.screens.LandingScreen
import com.example.usolo.features.screens.LoginScreen
import com.example.usolo.features.registration.ui.screens.SignUpScreen
import com.example.usolo.ui.theme.UsoloTheme
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usolo.features.auth.data.repository.AuthRepository
import com.example.usolo.features.auth.ui.viewmodel.AuthViewModel
import com.example.usolo.features.registration.ui.screens.EmailSignUpScreen
import com.example.usolo.features.registration.ui.viewmodel.SignUpViewModel
import com.example.usolo.features.menu.ui.screens.MainMenu
import com.example.usolo.features.rental_registration.ui.screens.RentalRegistrationScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AppVariables")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalDataSourceProvider.init(applicationContext.dataStore)

        enableEdgeToEdge()
        CoroutineScope(Dispatchers.IO).launch {
            val authRepo = AuthRepository()
            authRepo.debugAuthData()
        }
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
    val authViewModel: AuthViewModel = viewModel()
    val localDataStore = LocalDataSourceProvider.get()
    val userIdState = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        authViewModel.getAuthStatus()
    }

    LaunchedEffect(Unit) {
        localDataStore.load("user_id").collect { userId ->
            userIdState.value = userId
        }
    }

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
        composable("rental_registration") {
            val localDataStore = LocalDataSourceProvider.get()
            val profileIdFlow = localDataStore.getProfileId()
            val profileId by profileIdFlow.collectAsState(initial = null)


            if (profileId == null) {
                // Aún no ha cargado, muestra loading o espera
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else if (profileId!!.isEmpty()) {
                // Ya cargó y está vacío → redirigir al login
                LaunchedEffect(Unit) {
                    loginController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            } else {
                // Ya cargó y es válido
                RentalRegistrationScreen(
                    navController = loginController,
                    userId = profileId!!,
                    onCreateProductClick = { /*...*/ },
                    onCreateRentalClick = { /*...*/ }
                )
            }

        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val loginController = rememberNavController()
    SignUpScreen(loginController)
}