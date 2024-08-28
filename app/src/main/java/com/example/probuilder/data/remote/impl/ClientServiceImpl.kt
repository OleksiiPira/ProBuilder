package com.example.probuilder.data.remote.impl

import com.example.probuilder.data.remote.ClientService
import com.example.probuilder.domain.model.Client
import com.example.probuilder.domain.use_case.auth.AccountService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClientServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : ClientService {

    private val projectsCollection = firestore.collection(USERS_COLLECTION).document(USER_ID).collection(PROJECTS_COLLECTION)
    override fun getClientByProjectId(projectId: String): Flow<Client> = callbackFlow {
        val listenerRegistration = projectsCollection.document(projectId).addSnapshotListener { snapshot, error ->
            if (error != null) { return@addSnapshotListener }
            projectsCollection.document(projectId).collection(CLIENT_COLLECTION)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener
                    snapshot?.let { it.toObjects(Client::class.java)
                        .getOrNull(0)?.let { client -> trySend(client).isSuccess }
                    }
                }
        }
        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun save(projectId: String, client: Client) {
        projectsCollection.document(projectId).collection(CLIENT_COLLECTION).add(client).await().id
    }

    override suspend fun update(projectId: String, client: Client) {
        projectsCollection

        val workerDocRef = projectsCollection.document(projectId).collection(CLIENT_COLLECTION).document(client.id)
        workerDocRef.set(client)
            .addOnSuccessListener {
                println("Client document replaced successfully")
            }
            .addOnFailureListener { e ->
                println("Error replacing client document: $e")
            }
    }

    override suspend fun delete(projectId: String, clientId: String) {
        projectsCollection.document(projectId).collection(CLIENT_COLLECTION).document(clientId).delete().await()
    }

    companion object {
        private const val USER_ID = "bOTQWZnTpjQ8uajIpU5x"
        private const val USERS_COLLECTION = "users"
        private const val PROJECTS_COLLECTION = "projects"
        private const val CLIENT_COLLECTION = "client"
    }
}