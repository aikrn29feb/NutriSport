package com.atulit.nutrisport.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Auth: Screen()

    @Serializable
    data object HomeGraph: Screen()

    @Serializable
    data object ProductsOverview: Screen()

    @Serializable
    data object Cart: Screen()

    @Serializable
    data object Categories: Screen()

    @Serializable
    data object Profile: Screen()

    @Serializable
    data object AdminPanel: Screen()

    @Serializable
    data class ManageProduct(
        val id: String? = null
    ): Screen()
}