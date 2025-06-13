package com.atulit.nutrisport.shared.domain

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class CartItem(
    val id: String = Uuid.random().toHexString(),
    val productId: String,
    val flavour: String? = null,
    val quantity: Int,

    )
