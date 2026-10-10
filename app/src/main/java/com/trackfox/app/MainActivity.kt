package com.trackfox.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trackfox.app.service.UserSessionManager
import com.trackfox.app.screen.AddTrainingScreen
import com.trackfox.app.screen.LoginScreen
import com.trackfox.app.screen.MainScreen
import com.trackfox.app.screen.RegistrationScreen
import com.trackfox.app.ui.theme.TrackFoxTheme
import com.trackfox.app.viewmodel.AddTrainingViewModel
import com.trackfox.app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackFoxTheme {
                val navController = rememberNavController()
                val viewModel : MainViewModel = hiltViewModel(
                    checkNotNull(LocalViewModelStoreOwner.current) {
                        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                    },
                    null
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (viewModel.currentUserId != -1L) "home" else "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            MainScreen { navController.navigate("add") }
                        }

                        composable("add") {
                            AddTrainingScreen { navController.popBackStack() }
                        }

                        composable("login") {
                            LoginScreen(
                                onNavigateToRegister = { navController.navigate("register") },
                                onSuccess = { navController.navigate("home") }
                            )
                        }

                        composable("register") {
                            RegistrationScreen { navController.navigate("home") }
                        }
                    }
                }
            }
        }
    }
}