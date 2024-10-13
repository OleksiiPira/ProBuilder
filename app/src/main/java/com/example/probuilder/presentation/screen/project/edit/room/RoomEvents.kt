package com.example.probuilder.presentation.screen.project.edit.room

import com.example.probuilder.domain.model.RoomSurface
import com.example.probuilder.domain.model.SurfaceType

sealed interface UpsertRoomEvent {
    data class SetName(val name: String): UpsertRoomEvent
    data class SetImageUrl(val imageUrl: String): UpsertRoomEvent
    data object Save: UpsertRoomEvent

}

sealed interface UpsertSurfaceEvent {
    data class SetName(val name: String): UpsertSurfaceEvent
    data class SetType(val type: SurfaceType): UpsertSurfaceEvent
    data class SetHeight(val height: Double): UpsertSurfaceEvent
    data class SetWidth(val width: Double): UpsertSurfaceEvent
    data class SetLength(val length: Double): UpsertSurfaceEvent
    data class SetDepth(val depth: Double): UpsertSurfaceEvent
    data object Save: UpsertSurfaceEvent
    data class Delete(val surface: RoomSurface): UpsertSurfaceEvent
}