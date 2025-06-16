package com.atulit.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atulit.nutrisport.auth.AuthScreen
import com.atulit.nutrisport.home.HomeGraphScreen

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Auth) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Auth> {
            AuthScreen(
                navigateToHome = {
                    navController.navigate(Screen.HomeGraph) {
                        popUpTo(Screen.Auth) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Screen.HomeGraph> {
            HomeGraphScreen()
        }

    }
}