package com.example.usolo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.usolo.features.screens.LandingScreen
import com.example.usolo.features.screens.LoginScreen
import com.example.usolo.features.screens.SignUpScreen
import com.example.usolo.ui.theme.UsoloTheme
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
fun App(){
    val loginController = rememberNavController()
    NavHost(navController = loginController, startDestination = "landing"){

        composable("landing"){
            LandingScreen(loginController = loginController)
        }
        composable("login"){
            LoginScreen(loginController = loginController)
        }
        composable("signup"){
            SignUpScreen(loginController = loginController)
        }


    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val loginController = rememberNavController()
    SignUpScreen(loginController)
}