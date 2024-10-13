package com.example.probuilder.domain.model

import com.example.probuilder.presentation.screen.categories.categories.ItemState
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.util.UUID

data class Job(
    @DocumentId val id: String = UUID.randomUUID().toString(),
    val categoryId: String = "",
    val userId: String = "",
    val name: String = "",
    val pricePerUnit: Int = 0,
    val measureUnit: String = "",
    val state: ItemState = ItemState.DEFAULT,
    val tags: List<String> = emptyList()
    )

//@Entity(tableName = "categories")
data class Category(
    @DocumentId var id: String = "",
    var name: String = "",
    @Exclude var jobs: List<Job> = emptyList(),
    var jobsCount: Int = 0,
    val state: ItemState = ItemState.DEFAULT,
    val parentId: String = "",
    val userId: String = "",
    )

data class Project(
    @DocumentId var id: String = "",
    val imageUrl: String = "",
    val name: String = "Назва проекту проекту",
    val address: String = "м. Львів, вул. Карпенка 8а, 39",
    val clientName: String = "Іван Шевченко Андрійович",
    val price: Int = 200000,
    val startDate: String = "20.05.2024",
    val endDate: String = "20.05.2024",
    val progress: Float = 0.2F,
    val totalHours: Float = 0.0F,
    val completeHours: Float = 0.0F,
    @Exclude var client: Client = Client(),
    @Exclude var rooms: List<Room> = emptyList(),
    @Exclude var workers: List<Worker> = emptyList(),
    @Exclude var notes: List<Note> = emptyList()
)

data class Client(
    @DocumentId var id: String = "",
    val imageUrl: String = "",
    val name: String = "Тарас Григорович Шевченко",
    val email: String = "test-1234@gmail.com",
    val phone: String = "0965740000",
    val note: String = "",
)

data class Worker(
    @DocumentId var id: String = "",
    val imageUrl: String = "",
    val name: String = "Тарас Григорович Шевченко",
    val trade: String = "Маляр",
    val email: String = "test-1234@gmail.com",
    val phone: String = "0965740000",
    val note: String = ""
)

data class Room(
    @DocumentId var id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val totalHours: Float = 20.0F,
    val completeHours: Float = 10.0F,
    val surfaces: List<RoomSurface> = emptyList()
)

data class RoomSurface(
    var id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val type: SurfaceType = SurfaceType.WALL,
    val height: Double = 0.0,
    val width: Double = 0.0,
    val length: Double = 0.0,
    val depth: Double = 0.0,
    val openings: List<Opening> = emptyList()
) {
    val area: Double
        get() = when (type) {
            SurfaceType.WALL -> height * width
            SurfaceType.CEILING, SurfaceType.FLOOR -> width * length
            SurfaceType.OTHER -> if (height > 0) {
                openings.sumOf { width * height }
            } else {
                openings.sumOf { width * depth }
            }
        }
    val perimeter: Double
        get() = when (type) {
            SurfaceType.WALL -> 0.0
            SurfaceType.CEILING, SurfaceType.FLOOR -> 2 * (width + length)
            SurfaceType.OTHER -> 0.0
        }
//    val netArea: Double get() = area - openings.sumOf { it.width * it.height }
}

data class Opening(
    val name: String = "Отвір двері 1",
    val height: Double = 2.0,
    val width: Double = 1.1,
)

data class Note(
    @DocumentId var id: String = "",
    val name: String = "Назва нотатки",
    val description: String = "Опис нотатки"
)

