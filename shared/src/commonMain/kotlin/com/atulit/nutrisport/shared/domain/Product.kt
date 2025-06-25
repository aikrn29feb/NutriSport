package com.atulit.nutrisport.shared.domain

import androidx.compose.ui.graphics.Color
import com.atulit.nutrisport.shared.CategoryBlue
import com.atulit.nutrisport.shared.CategoryGreen
import com.atulit.nutrisport.shared.CategoryPurple
import com.atulit.nutrisport.shared.CategoryRed
import com.atulit.nutrisport.shared.CategoryYellow
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val category: String,
    val flavors: List<String>? = null,
    val weight: Int? = null,
    val price: Double,
    val isPopular: Boolean = false,
    val isNew: Boolean = false,
    val isDiscounted: Boolean = false,
    /*val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,*/
)

enum class ProductCategory(
    val title: String,
    val color: Color
) {
    Protein(
        title = "Protein",
        color = CategoryYellow
    ),
    Creatine(
        title = "Creatine",
        color = CategoryBlue
    ),
    PreWorkout(
        title = "Pre-Workout",
        color = CategoryGreen
    ),
    Gainers(
        title = "Gainers",
        color = CategoryPurple
    ),
    Accessories(
        title = "Accessories",
        color = CategoryRed
    )



}