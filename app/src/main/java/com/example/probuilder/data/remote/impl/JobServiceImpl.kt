package com.example.probuilder.data.remote.impl

import androidx.compose.ui.util.trace
import com.example.probuilder.data.remote.JobService
import com.example.probuilder.domain.model.Job
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

    private val categoriesCollection = firestore.collection(USERS_COLLECTION).document(USER_ID).collection(CATEGORY_COLLECTION)
    private val jobsCollection = firestore.collection(USERS_COLLECTION).document(USER_ID).collection(JOB_COLLECTION)
    private val metadataCollection = firestore.collection("metadata")

    override val jobs: Flow<List<Job>> = callbackFlow {
        val listenerRegistration = jobsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) try {
                    val categoriesList = snapshot.toObjects(Job::class.java)
                    trySend(categoriesList)
                } catch (e: Exception) {
                    close(e)
                }
        }

        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun save(job: Job): Boolean = trace(SAVE_JOB_TRACE) {
        return try {
            val jobDocRef = categoriesCollection.document(job.categoryId).collection("jobs").document(job.id)

            jobDocRef.set(job).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getJobById(jobId: String): Job? {
        return try {
            val documentSnapshot = firestore.collection("categories").document(jobId).get().await()
            if (documentSnapshot.exists()) documentSnapshot.toObject(Job::class.java)?.copy(id = jobId)
            else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun loadTags(onTagsLoaded: (List<String>) -> Unit) {
        try {
            val documentSnapshot = metadataCollection.document("job").get().await()
            val tags = documentSnapshot.get("tags") as? List<String> ?: emptyList()
            onTagsLoaded(tags)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun fetchJobs(
        categoryId: String,
        onSuccess: (List<Job>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        categoriesCollection.document(categoryId)
            .collection("jobs")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    onFailure(exception)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val jobsList = snapshot.toObjects(Job::class.java)
                    onSuccess(jobsList)
                }
            }
    }

    override suspend fun delete(job: Job) {
        val categoryRef = categoriesCollection.document(job.categoryId)
        categoryRef.update("jobsCount", FieldValue.increment(-1))
        jobsCollection.document(job.id).delete().await()
    }

    companion object {
        private const val USER_ID = "bOTQWZnTpjQ8uajIpU5x"
        private const val USERS_COLLECTION = "users"
        private const val CATEGORY_COLLECTION = "categories"
        private const val JOB_COLLECTION = "jobs"
        private const val SAVE_JOB_TRACE = "saveJob"
        private const val UPDATE_JOB_TRACE = "updateJob"
    }
}