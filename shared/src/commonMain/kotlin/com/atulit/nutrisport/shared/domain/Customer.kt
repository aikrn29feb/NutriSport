package com.atulit.nutrisport.shared.domain

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val photoURL: String? = null,
    val city: String? = null,
    val postalCode: Int? = null,
    val address: String? = null,
    val phoneNumber: PhoneNumber? = null,
    val cart: List<CartItem> = emptyList(),
    val isAdmin: Boolean = false // Add isAdmin property in Fire store as Sub collection

    )

@Serializable
data class PhoneNumber(
    val dialCode: Int,
    val number: String
)
