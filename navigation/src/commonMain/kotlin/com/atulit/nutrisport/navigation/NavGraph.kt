package com.atulit.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.atulit.nutrisport.admin_panel.AdminPanelScreen
import com.atulit.nutrisport.auth.AuthScreen
import com.atulit.nutrisport.home.HomeGraphScreen
import com.atulit.nutrisport.manage_product.ManageProductScreen
import com.atulit.nutrisport.profile.ProfileScreen
import com.atulit.nutrisport.shared.navigation.Screen

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
            HomeGraphScreen(
                navigateToAuth = {
                    navController.navigate(Screen.Auth) {
                        popUpTo(Screen.HomeGraph) {
                            inclusive = true
                        }
                    }
                },
                navigateToProfile = {
                    navController.navigate(Screen.Profile)
                },
                navigateToAdmin = {
                    navController.navigate(Screen.AdminPanel)
                }
            )
        }

        composable<Screen.Profile> {
            ProfileScreen(

                navigateToHome = {
                    navController.navigate(Screen.HomeGraph) {
                        popUpTo(Screen.Profile) {
                            inclusive = true
                        }
                    }
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Screen.AdminPanel> {
            AdminPanelScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToManageProduct = { id ->
                    navController.navigate(Screen.ManageProduct(id))
                }
            )

        }

        composable<Screen.ManageProduct> {
            val id = it.toRoute<Screen.ManageProduct>().id
            ManageProductScreen(
                id = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

    }
}