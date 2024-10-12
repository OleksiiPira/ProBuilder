package com.example.probuilder.data.remote.impl

import com.example.probuilder.data.remote.RoomService
import com.example.probuilder.domain.model.Room
import com.example.probuilder.domain.use_case.auth.AccountService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RoomServiceImpl @Inject constructor(
    firestore: FirebaseFirestore,
    auth: AccountService
) : RoomService {
    private val userId = auth.currentUserId
    private val projectsCollection = firestore.collection(USERS_COLLECTION).document(userId).collection(PROJECTS_COLLECTION)

    override fun getRooms(projectId: String): Flow<List<Room>> = callbackFlow {
        val listenerRegistration = projectsCollection.document(projectId).collection(ROOMS_COLLECTION).addSnapshotListener { snapshot, error ->
            if (error == null) {
                snapshot?.let {
                    val projectsList: MutableList<Room> = it.toObjects(Room::class.java)
                    trySend(projectsList).isSuccess
                }
                return@addSnapshotListener
            }
        }
        awaitClose { listenerRegistration.remove() }
    }


    override suspend fun save(projectId: String, room: Room) {
        projectsCollection.document(projectId).collection(ROOMS_COLLECTION).add(room).await().id
    }

    override suspend fun update(projectId: String, room: Room) {
        val roomDocRef = projectsCollection.document(projectId).collection(ROOMS_COLLECTION).document(room.id)
        roomDocRef.set(room)
            .addOnSuccessListener {
                println("Room document replaced successfully")
            }
            .addOnFailureListener { e ->
                println("Error replacing room document: $e")
            }
    }
    
    override suspend fun delete(projectId: String, roomId: String) {
        projectsCollection.document(projectId).collection(ROOMS_COLLECTION).document(roomId).delete().await()
    }

    override fun getRoomById(projectId: String, roomId: String): Flow<Room> = callbackFlow {
        if (roomId.isBlank()) {
            close(IllegalArgumentException("Room ID cannot be empty"))
            return@callbackFlow
        }
        val listenerRegistration = projectsCollection.document(projectId).collection(ROOMS_COLLECTION).document(roomId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                snapshot?.let {
                    val room = snapshot.toObject(Room::class.java)
                    room?.let { trySend(room).isSuccess }
                }
            }
        awaitClose { listenerRegistration.remove() }
    }

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val PROJECTS_COLLECTION = "projects"
        private const val ROOMS_COLLECTION = "rooms"
    }
}