package com.atulit.nutrisport.home.domain

import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.navigation.Screen
import org.jetbrains.compose.resources.DrawableResource

enum class BottomBarDestination(
    val icon: DrawableResource,
    val title: String,
    val screen: Screen,
    val route: String
) {

    ProductsOverview(
        icon = Resources.Icon.Home,
        title = "Nutri Sport ",
        screen = Screen.ProductsOverview,
        route = "products_overview"
    ),
    Cart(
        icon =  Resources.Icon.ShoppingCart,
        title = "Cart",
        screen = Screen.Cart,
        route = "cart"
    ),
    Categories(
        icon = Resources.Icon.Categories,
        title = "Categories",
        screen = Screen.Categories,
        route = "categories"

    )
}