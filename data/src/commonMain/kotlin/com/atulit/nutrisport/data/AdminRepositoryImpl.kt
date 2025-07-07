package com.atulit.nutrisport.data

import com.atulit.nutrisport.data.domain.AdminRepository
import com.atulit.nutrisport.shared.domain.Product
import com.atulit.nutrisport.shared.util.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
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

            getCurrentUserId()?.let {
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
            try {
                withTimeout(timeMillis = 20000L) {
                    imagePath.putFile(file)
                    imagePath.getDownloadUrl()
                }
            } catch (e: Exception) {
                println("Error while uploading image to storage : ${e.message}")
                null
            }
        } else null
    }

    override suspend fun deleteImageFromStorage(
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val storagePath = extractFirebaseStoragePath(downloadUrl)
            storagePath?.let {
                Firebase.storage.reference(it).delete()
                onSuccess()
            }
                ?: onError("Error while deleting image from storage")
            onSuccess()

        } catch (e: Exception) {
            onError("Error while deleting image from storage : ${e.message}")
        }

    }

    override fun readLastTenProducts(): Flow<RequestState<List<Product>>> = channelFlow {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                database.collection(
                    collectionPath = "products"
                )
                    .orderBy("createdAt", Direction.DESCENDING )
                    .limit(10)
                    .snapshots
                    .collectLatest { query ->
                        val products = query.documents.map { document ->
                            Product(
                                id = document.id,
                                title = document.get(field = "title"),
                                createdAt = document.get(field = "createdAt"),
                                description = document.get(field = "description"),
                                thumbnail = document.get(field = "thumbnail"),
                                category = document.get(field = "category"),
                                flavors = document.get(field = "flavors"),
                                weight = document.get(field = "weight"),
                                price = document.get(field = "price"),
                                isPopular = document.get(field = "isPopular"),
                                isNew = document.get(field = "isNew"),
                                isDiscounted = document.get(field = "isDiscounted"),
                            )
                        }
                        send(
                            RequestState.Success(products)
                        )
                    }
            } else {
                send(
                    RequestState.Error("Products are not available")
                )
            }

        } catch (e: Exception) {
            send(
                RequestState.Error(
                    "Error while reading last 10 products" +
                            "from the database - ${e.message.toString()}"
                )
            )
        }
    }


    private fun extractFirebaseStoragePath(downloadUrl: String): String? {
        val startIndex = downloadUrl.indexOf("o/") + 2
        if (startIndex < 3) {
            return null
        }
        val endIndex = downloadUrl.indexOf("?", startIndex)
        val encodePath = if (endIndex != -1) {
            downloadUrl.substring(startIndex, endIndex)
        } else {
            downloadUrl.substring(startIndex, endIndex)
        }
        return decodeFirebasePath(encodePath)

    }

    private fun decodeFirebasePath(encodedPath: String): String {
        return encodedPath
            .replace("%2F", "/")
            .replace("%20", " ")
    }
}