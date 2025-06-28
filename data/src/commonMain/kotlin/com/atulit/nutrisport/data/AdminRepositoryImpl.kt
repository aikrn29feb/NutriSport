package com.atulit.nutrisport.data

import com.atulit.nutrisport.data.domain.AdminRepository
import com.atulit.nutrisport.shared.domain.Product
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.coroutines.withTimeout
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AdminRepositoryImpl : AdminRepository {
    override fun getCurrentUserId(): String? = Firebase.auth.currentUser?.uid

    override suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {

            getCurrentUserId() ?.let {
                val firestore = Firebase.firestore
                val productCollection = firestore.collection(
                    collectionPath = "products"
                )
                productCollection.document(
                    product.id
                ).set(
                    product
                )

                onSuccess()
            } ?: onError("User is not available")


        } catch (e: Exception) {
            onError("Error while creating a new product : ${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun uploadImageToStorage(file: File): String? {
        return if (getCurrentUserId() != null) {
            val storage = Firebase.storage.reference
            val imagePath = storage.child("images/${Uuid.random().toHexString()}")
            try{
                withTimeout(timeMillis = 20000L) {
                    imagePath.putFile(file)
                    imagePath.getDownloadUrl()
                }
            } catch (e: Exception){
                null
            }
        } else null
    }
}