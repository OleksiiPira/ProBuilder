package com.example.probuilder.data.remote.impl

import androidx.compose.ui.util.trace
import com.example.probuilder.data.remote.ProjectService
import com.example.probuilder.domain.model.Client
import com.example.probuilder.domain.model.Note
import com.example.probuilder.domain.model.Project
import com.example.probuilder.domain.model.Room
import com.example.probuilder.domain.model.Worker
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

    private val projectsCollection =
        firestore.collection(USERS_COLLECTION).document(USER_ID).collection(PROJECTS_COLLECTION)


    override val projects: Flow<List<Project>> = callbackFlow {
        val listenerRegistration = projectsCollection.addSnapshotListener { snapshot, error ->
            if (error == null) {
                snapshot?.let {
                    val projectsList: MutableList<Project> = it.toObjects(Project::class.java)
                    // get client
                    projectsList.forEach { project -> populateProjectsData(project) }

                    trySend(projectsList).isSuccess
                }
                return@addSnapshotListener
            }
        }
        awaitClose { listenerRegistration.remove() }
    }

    private fun populateProjectsData(project: Project) : Project {
        projectsCollection.document(project.id).collection(CLIENT_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if (error == null) {
                    snapshot?.let {
                        val clientList = it.toObjects(Client::class.java)
                        project.client = clientList.getOrNull(0) ?: Client()
                    }
                }
            }
        projectsCollection.document(project.id).collection(ROOMS_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if (error == null) {
                    snapshot?.let {
                        project.rooms = it.toObjects(Room::class.java)
                    }
                }
            }
        projectsCollection.document(project.id).collection(WORKERS_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if (error == null) {
                    snapshot?.let {
                        project.workers = it.toObjects(Worker::class.java)
                    }
                }
            }
        projectsCollection.document(project.id).collection(NOTES_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if (error == null) {
                    snapshot?.let {
                        project.notes = it.toObjects(Note::class.java)
                    }
                }
            }
        return project
    }


    override suspend fun save(project: Project): String = trace(SAVE_PROJECT_TRACE) {
        projectsCollection.add(project).await().id
    }

    override fun getProjectById(projectId: String): Flow<Project> = callbackFlow {
        val listenerRegistration = projectsCollection.document(projectId).addSnapshotListener { snapshot, error ->
            if (error != null) { return@addSnapshotListener }
            var project = snapshot?.toObject(Project::class.java)
            project?.let { project = populateProjectsData(it)  }
            trySend(project?: Project()).isSuccess
        }
        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun delete(projectId: String) {
        projectsCollection.document(projectId).delete().await()
    }

    companion object {
        private const val USER_ID = "bOTQWZnTpjQ8uajIpU5x"
        private const val USERS_COLLECTION = "users"
        private const val PROJECTS_COLLECTION = "projects"
        private const val CLIENT_COLLECTION = "client"
        private const val ROOMS_COLLECTION = "rooms"
        private const val WORKERS_COLLECTION = "workers"
        private const val NOTES_COLLECTION = "notes"
        private const val SAVE_PROJECT_TRACE = "saveCategory"
    }
}