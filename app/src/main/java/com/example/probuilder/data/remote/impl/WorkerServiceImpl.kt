package com.example.probuilder.data.remote.impl

import com.example.probuilder.data.remote.WorkerService
import com.example.probuilder.domain.model.Worker
import com.example.probuilder.domain.use_case.auth.AccountService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkerServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : WorkerService {

    private val projectsCollection = firestore.collection(USERS_COLLECTION).document(USER_ID).collection(PROJECTS_COLLECTION)
    override fun getWorkerById(projectId: String, workerId: String): Flow<Worker> = callbackFlow {
        val listenerRegistration = projectsCollection.document(projectId).addSnapshotListener { snapshot, error ->
            if (error != null) { return@addSnapshotListener }
            projectsCollection.document(projectId).collection(WORKERS_COLLECTION)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener
                    snapshot?.let { it.toObjects(Worker::class.java).firstOrNull { it.id == workerId }
                        ?.let {
                            worker -> trySend(worker).isSuccess
                        }
                    }
                }
        }
        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun save(projectId: String, worker: Worker) {
        projectsCollection.document(projectId).collection(WORKERS_COLLECTION).add(worker).await().id
    }

    override suspend fun update(projectId: String, worker: Worker) {
        projectsCollection

        val workerDocRef = projectsCollection.document(projectId).collection(WORKERS_COLLECTION).document(worker.id)
        workerDocRef.set(worker)
            .addOnSuccessListener {
                println("Worker document replaced successfully")
            }
            .addOnFailureListener { e ->
                println("Error replacing worker document: $e")
            }
    }
    
    override suspend fun delete(projectId: String, workerId: String) {
        projectsCollection.document(projectId).collection(WORKERS_COLLECTION).document(workerId).delete().await()
    }

    companion object {
        private const val USER_ID = "bOTQWZnTpjQ8uajIpU5x"
        private const val USERS_COLLECTION = "users"
        private const val PROJECTS_COLLECTION = "projects"
        private const val WORKERS_COLLECTION = "workers"
    }
}