package com.example.probuilder.data.remote.impl

import androidx.compose.ui.util.trace
import com.example.probuilder.data.remote.CategoryService
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.use_case.auth.AccountService
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : CategoryService {

    private val firestoreCategories = firestore.collection(USERS_COLLECTION).document(USER_ID).collection(CATEGORY_COLLECTION)
    private val firestoreServices = firestore.collection(USERS_COLLECTION).document(USER_ID).collection(SERVICES_COLLECTION)


    override val categories: Flow<List<Category>> = callbackFlow {
        val listenerRegistration = firestoreCategories.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            snapshot?.let {
                val categoriesList = it.toObjects(Category::class.java)

                val jobsFetches = categoriesList.map { category ->
                    firestoreCategories.document(category.id).collection("jobs").get()
                        .continueWith { task ->
                            if (task.isSuccessful) category.copy(jobsCount = task.result?.size() ?: 0)
                            else category
                        }
                }

                Tasks.whenAllComplete(jobsFetches).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val updatedCategoriesList = jobsFetches.mapNotNull { it.result }
                        trySend(updatedCategoriesList).isSuccess
                    } else {
                        task.exception?.let { close(it) }
                    }
                }
            }
        }

        awaitClose { listenerRegistration.remove() }
    }
    override suspend fun save(category: Category): String = trace(SAVE_CATEGORY_TRACE) {
        val categoryWithUserId = category.copy(userId = auth.currentUserId)
        firestoreCategories.add(categoryWithUserId).await().id
    }

    override suspend fun getCategory(categoryId: String): Category? {
        return try {
            val documentSnapshot = firestoreCategories.document(categoryId).get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(Category::class.java)?.copy(id = categoryId)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun delete(categoryId: String) {
        val childCategoriesQuerySnapshot = firestoreServices.whereEqualTo("categoryId", categoryId).get().await()

        for (job in childCategoriesQuerySnapshot.documents) {
            firestoreServices.document(job.id).delete().await()
        }

        firestoreCategories.document(categoryId).delete().await()
    }

    companion object {
        private const val USER_ID = "bOTQWZnTpjQ8uajIpU5x"
        private const val USERS_COLLECTION = "users"
        private const val CATEGORY_COLLECTION = "categories"
        private const val SERVICES_COLLECTION = "services"
        private const val SAVE_CATEGORY_TRACE = "saveCategory"
        private const val UPDATE_CATEGORY_TRACE = "updateCategory"
    }
}