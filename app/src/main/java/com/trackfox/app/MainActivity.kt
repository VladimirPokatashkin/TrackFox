package com.trackfox.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trackfox.app.model.service.UserSessionManager
import com.trackfox.app.screen.AddTrainingScreen
import com.trackfox.app.screen.LoginScreen
import com.trackfox.app.screen.MainScreen
import com.trackfox.app.screen.RegistrationScreen
import com.trackfox.app.ui.theme.TrackFoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackFoxTheme {
                val navController = rememberNavController()
                val sessionManager = UserSessionManager(application)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (sessionManager.getCurrentUserId() != -1L) "home" else "register",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            MainScreen { navController.navigate("add") }
                        }

                        composable("add") {
                            AddTrainingScreen { navController.popBackStack() }
                        }

                        composable("login") {
                            LoginScreen { navController.navigate("register") }
                        }

                        composable("register") {
                            RegistrationScreen()
                        }
                    }
                }
            }
        }
    }
}