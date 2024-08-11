package com.example.probuilder.data.remote.impl

import androidx.compose.ui.util.trace
import com.example.probuilder.data.remote.JobService
import com.example.probuilder.domain.model.Service
import com.example.probuilder.domain.use_case.auth.AccountService
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class JobServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : JobService {

    private val jobsCollection = firestore.collection("services")
    private val categoriesCollection = firestore.collection(CATEGORY_COLLECTION)

    override val jobs: Flow<List<Service>> = callbackFlow {
    val listenerRegistration = jobsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                try {
                    val categoriesList = snapshot.toObjects(Service::class.java)
                    trySend(categoriesList)
                } catch (e: Exception) {
                    close(e)
                }
            }
        }

    awaitClose { listenerRegistration.remove() }
}

    override suspend fun save(job: Service): String = trace(SAVE_CATEGORY_TRACE) {
        val categoryRef = categoriesCollection.document(job.categoryId)
        categoryRef.update("jobsCount", FieldValue.increment(1))

        val serviceWithUserId = job.copy(userId = auth.currentUserId)
        jobsCollection.add(serviceWithUserId).await().id
    }
    

    override suspend fun getJobById(jobId: String): Service? {
        return try {
            val documentSnapshot = firestore.collection("categories").document(jobId).get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(Service::class.java)?.copy(id = jobId)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun delete(job: Service) {
        val categoryRef = categoriesCollection.document(job.categoryId)
        categoryRef.update("jobsCount", FieldValue.increment(-1))
        jobsCollection.document(job.id).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val CATEGORY_COLLECTION = "categories"
        private const val SAVE_CATEGORY_TRACE = "saveService"
        private const val UPDATE_CATEGORY_TRACE = "updateService"
    }
}