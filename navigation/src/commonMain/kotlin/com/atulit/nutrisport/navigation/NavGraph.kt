package com.atulit.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atulit.nutrisport.auth.AuthScreen

@Composable
fun SetupNavGraph() {

    NavHost(
        navController = rememberNavController(),
        startDestination = Screen.Auth
    ) {
        composable<Screen.Auth> {
            AuthScreen()
        }
    }
}