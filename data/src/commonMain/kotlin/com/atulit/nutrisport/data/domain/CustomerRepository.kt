package com.atulit.nutrisport.data.domain

import com.atulit.nutrisport.shared.util.RequestState
import dev.gitlive.firebase.auth.FirebaseUser

interface CustomerRepository {

    suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: ()-> Unit,
        onError: (String)-> Unit,
    )


    fun getCustomerId(): String?

    suspend fun signOut(): RequestState<Unit>
}