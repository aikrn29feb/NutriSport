package com.atulit.nutrisport.data

import com.atulit.nutrisport.data.domain.CustomerRepository
import com.atulit.nutrisport.shared.domain.Customer
import com.atulit.nutrisport.shared.util.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

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
                    lastName = user.displayName?.split(" ")?.lastOrNull() ?: "Unknown second name",
                    email = user.email?.toString() ?: "unknown email",
                    photoURL = user.photoURL?.toString() ?: "unknown photo url"
                )

                val customerExists = customerCollection.document(user.uid).get().exists
                if (customerExists) {
                    onSuccess()
                } else {
                    customerCollection.document(user.uid).set(customer)
                    customerCollection.document(user.uid)
                        .collection("privateData")              // new private data collection - sub collection
                        .document("role")
                        .set(mapOf("isAdmin" to false, ))
                    onSuccess()
                }

            } else {
                onError("User is not available")
            }

        } catch (e: Exception) {
            onError("Error while creating customer - ${e.message.toString()}")

        }
    }

    override fun getCustomerId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    override suspend fun signOut(): RequestState<Unit> {
        return try {
            Firebase.auth.signOut()
            RequestState.Success(data = Unit)
        } catch (e: Exception) {
            RequestState.Error("Error while signing out ${e.message}")
        }
    }

    override fun readCustomerFlow(): Flow<RequestState<Customer>> = channelFlow {
        try {
            val userId = getCustomerId()
            if (userId != null) {
                val database = Firebase.firestore
                database.collection(collectionPath = "customers")
                    .document(userId)
                    .snapshots
                    .collectLatest { document ->
                        if (document.exists) {
                            val privateDataDocument = database.collection(collectionPath = "customers")
                                .document(userId)
                                .collection("privateData")
                                .document("role")
                                .get()
                            val customer = Customer(
                                id = document.id,
                                firstName = document.get(field = "firstName"),
                                lastName = document.get(field = "lastName"),
                                email = document.get(field = "email"),
                                photoURL = document.get(field = "photoURL"),
                                city = document.get(field = "city"),
                                postalCode = document.get(field = "postalCode"),
                                address = document.get(field = "address"),
                                phoneNumber = document.get(field = "phoneNumber"),
                                cart = document.get(field = "cart"),
                                isAdmin = privateDataDocument.get(field = "isAdmin")
                            )

                            send(RequestState.Success(data = customer))
                        } else {
                            send(RequestState.Error("Customer document does not exist"))
                        }

                    }
            } else {
                send(RequestState.Error("User is not available"))
            }
        } catch (e: Exception) {
            send(RequestState.Error("Error while reading customer flow ${e.message}"))
        }

    }

    override suspend fun updateCustomer(
        customer: Customer,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userId = getCustomerId()
            if (userId != null) {
                val firestore = Firebase.firestore
                val customerCollection = firestore.collection(collectionPath = "customers")

                val existingCustomer = customerCollection.document(customer.id).get()
                if (existingCustomer.exists) {
                    customerCollection.document(customer.id)
                        .update(
                            "firstName" to customer.firstName,
                            "lastName" to customer.lastName,
                            "city" to customer.city,
                            "postalCode" to customer.postalCode,
                            "address" to customer.address,
                            "phoneNumber" to customer.phoneNumber
                        )
                    onSuccess()
                } else {
                    onError("Customer not found.")
                }
            } else {
                onError("User is not available.")
            }

        } catch (e: Exception) {
            onError("Error while updating a Customer information: ${e.message}")
        }
    }


}