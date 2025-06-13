package com.atulit.nutrisport.data

import com.atulit.nutrisport.data.domain.CustomerRepository
import com.atulit.nutrisport.shared.domain.Customer
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.auth.FirebaseUser

class CustomerRepositoryImpl : CustomerRepository {

    override suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            if (user != null) {
                val customerCollection = Firebase.firestore.collection(collectionPath = "customers")

                val customer = Customer(
                    id = user.uid,
                    firstName = user.displayName?.split(" ")?.firstOrNull() ?: "Unknown first name",
                    lastName =  user.displayName?.split(" ")?.lastOrNull() ?: "Unknown second name",
                    email = user.email?.toString() ?: "unknown email",
                )

                val customerExists = customerCollection.document(user.uid).get().exists
                if (customerExists) {
                    onSuccess()
                } else {
                    customerCollection.document(user.uid).set(customer)
                    onSuccess()
                }

            } else {
                onError("User is not available")
            }

        } catch (e : Exception) {
            onError("Error while creating customer - ${e.message.toString()}")

        }
    }

}