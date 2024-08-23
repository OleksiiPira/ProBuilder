package com.example.probuilder.data.remote.impl

import androidx.compose.ui.util.trace
import com.example.probuilder.data.remote.ProjectService
import com.example.probuilder.domain.model.Project
import com.example.probuilder.domain.model.User
import com.example.probuilder.domain.use_case.auth.AccountService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProjectServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : ProjectService {

    private val projectsCollection = firestore.collection(USERS_COLLECTION).document(USER_ID).collection(PROJECTS_COLLECTION)


    override val projects: Flow<List<Project>> = callbackFlow {
        val listenerRegistration = projectsCollection.addSnapshotListener { snapshot, error ->
            if (error == null) {
                snapshot?.let {
                    val projectsList: MutableList<Project> = it.toObjects(Project::class.java)
                    // get client
                    projectsList.forEach { project ->
                        projectsCollection.document(project.id).collection(CLIENT_COLLECTION)
                            .addSnapshotListener { snapshot, error ->
                                if (error == null) {
                                    snapshot?.let {
                                        val clientList = it.toObjects(User::class.java)
                                        project.client = clientList.getOrNull(0) ?: User()
                                    }
                                }
                            }
                    }
                    trySend(projectsList).isSuccess
                }
                return@addSnapshotListener
            }
        }
        awaitClose { listenerRegistration.remove() }
    }


    override suspend fun save(project: Project): String = trace(SAVE_PROJECT_TRACE) {
        projectsCollection.add(project).await().id
    }

    override suspend fun getProject(projectId: String): Project? {
        return try {
            val documentSnapshot = projectsCollection.document(projectId).get().await()
            if (documentSnapshot.exists()) documentSnapshot.toObject(Project::class.java)?.copy(id = projectId)
            else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun delete(projectId: String) {
        projectsCollection.document(projectId).delete().await()
    }

    companion object {
        private const val USER_ID = "bOTQWZnTpjQ8uajIpU5x"
        private const val USERS_COLLECTION = "users"
        private const val PROJECTS_COLLECTION = "projects"
        private const val CLIENT_COLLECTION = "client"
        private const val SAVE_PROJECT_TRACE = "saveCategory"
    }
}