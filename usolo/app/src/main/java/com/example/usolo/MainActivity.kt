package com.example.usolo
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Importaciones de tus pantallas
import com.example.usolo.screens.LandingScreen
import com.example.usolo.features.screens.LoginScreen
import com.example.usolo.features.registration.ui.screens.SignUpScreen
import com.example.usolo.features.registration.ui.screens.EmailSignUpScreen
import com.example.usolo.features.menu.ui.screens.MainMenu
import com.example.usolo.features.products.ui.screens.EditProductScreen
import com.example.usolo.features.products.ui.screens.ViewProductsScreen
import com.example.usolo.features.rental_registration.ui.screens.RentalRegistrationScreen
import com.example.usolo.features.postobject.ui.screens.PublicObjet
import com.example.usolo.features.settings.ui.SettingsScreen

// Importaciones de dependencias
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.auth.data.repository.AuthRepositoryImpl
import com.example.usolo.features.auth.ui.viewmodel.AuthViewModel
import com.example.usolo.features.registration.ui.viewmodel.SignUpViewModel
import com.example.usolo.ui.theme.UsoloTheme


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AppVariables")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalDataSourceProvider.init(applicationContext.dataStore)
        enableEdgeToEdge()

        CoroutineScope(Dispatchers.IO).launch {
            val authRepo = AuthRepositoryImpl()
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
            enterTransition = NavigationAnimations.Combinations.mainScreen.enter,
            exitTransition = NavigationAnimations.Combinations.mainScreen.exit
        ) {
            LandingScreen(navController = loginController)
        }


        composable(
            "login",
            enterTransition = NavigationAnimations.Combinations.subtle.enter,
            exitTransition = NavigationAnimations.Combinations.subtle.exit
        ) {
            LoginScreen(loginController = loginController)
        }


        composable(
            "signup",
            enterTransition = NavigationAnimations.Combinations.authFlow.enter,
            exitTransition = NavigationAnimations.Combinations.authFlow.exit
        ) {
            SignUpScreen(loginController = loginController)
        }


        composable(
            "email_signup",
            enterTransition = NavigationAnimations.Combinations.sideNavigation.enter,
            exitTransition = NavigationAnimations.Combinations.sideNavigation.exit
        ) {
            EmailSignUpScreen(navController = loginController, viewModel = signUpViewModel)
        }


        composable(
            "menu",
            enterTransition = NavigationAnimations.Combinations.modal.enter,
            exitTransition = NavigationAnimations.Combinations.modal.exit
        ) {
            MainMenu(loginController = loginController)
        }


        composable(
            "settings",
            enterTransition = NavigationAnimations.slideFromBottom(),
            exitTransition = NavigationAnimations.slideToBottom()
        ) {
            SettingsScreen(loginController = loginController)
        }

        composable(
            "products",
            enterTransition = NavigationAnimations.Combinations.sideNavigation.enter,
            exitTransition = NavigationAnimations.Combinations.sideNavigation.exit
        ) {
            ViewProductsScreen(navController = loginController)
        }

        composable(
            "edit_product/{productId}",
            enterTransition = NavigationAnimations.Combinations.detailScreen.enter,
            exitTransition = NavigationAnimations.Combinations.detailScreen.exit
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null) {
                EditProductScreen(loginController, productId = productId)
            }
        }

        composable(
            "rental_registration",
            enterTransition = NavigationAnimations.Combinations.sideNavigation.enter,
            exitTransition = NavigationAnimations.Combinations.sideNavigation.exit
        ) {
            val localDataStore = LocalDataSourceProvider.get()
            val profileIdFlow = localDataStore.getProfileId()
            val profileId by profileIdFlow.collectAsState(initial = null)

            if (profileId == null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else if (profileId!!.isEmpty()) {
                LaunchedEffect(Unit) {
                    loginController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            } else {
                RentalRegistrationScreen(
                    navController = loginController,
                    userId = profileId!!,
                    onCreateProductClick = { loginController.navigate("PublishProduct") },
                    onCreateRentalClick = { /* future implementation */ }
                )
            }
        }


        composable(
            "PublishProduct",
            enterTransition = NavigationAnimations.Combinations.authFlow.enter,
            exitTransition = NavigationAnimations.Combinations.authFlow.exit
        ) {
            PublicObjet(loginController = loginController)
        }
    }
}

/**
 * Extensiones para hacer el código aún más limpio
 */
object AppAnimations {

    val welcomeScreen = NavigationAnimations.Combinations.mainScreen
    val userFlow = NavigationAnimations.Combinations.authFlow
    val contentNavigation = NavigationAnimations.Combinations.sideNavigation
    val detailView = NavigationAnimations.Combinations.detailScreen
    val modalView = NavigationAnimations.Combinations.modal
    val quickTransition = NavigationAnimations.Combinations.subtle
}