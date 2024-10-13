package com.example.probuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.probuilder.common.ext.toJSON
import com.example.probuilder.common.ext.toMeasure
import com.example.probuilder.common.ext.toTitle
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.domain.model.Room
import com.example.probuilder.domain.model.RoomSurface
import com.example.probuilder.domain.model.SurfaceType
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.screen.project.details.room.RoomSurfaceCard
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun RoomSurfaces(
    room: Room,
    projectId: String,
    navigateTo: (String) -> Unit,
    deleteSurface: (RoomSurface) -> Unit
) {
    val showSurfaceEditScreen = { surface: RoomSurface -> navigateTo(
        Route.EDIT_SURFACE_SCREEN
            .replace("{projectId}", projectId)
            .replace("{room}", room.toJSON())
            .replace("{surface}", surface.toJSON())
    )}

    val surfacesMap = mutableMapOf<SurfaceType, MutableList<RoomSurface>>(
        SurfaceType.WALL to mutableListOf(),
        SurfaceType.CEILING to mutableListOf(),
        SurfaceType.FLOOR to mutableListOf(),
        SurfaceType.OTHER to mutableListOf()
    )
    room.surfaces.forEach { surfacesMap[it.type]?.add(it) }
    surfacesMap.filter { it.value.isNotEmpty() }.forEach { pair ->
        SurfaceSection(
            name = pair.key.label,
            room = room,
            surfaces = pair.value,
            showSurfaceEditScreen = showSurfaceEditScreen,
            deleteSurface = deleteSurface
        )
    }
}

@Composable
fun SurfaceSection(
    name: String,
    room: Room,
    surfaces: List<RoomSurface>,
    showSurfaceEditScreen: (RoomSurface) -> Unit,
    deleteSurface: (RoomSurface) -> Unit
) {
    Column {
        Text(
            text = name.toTitle(),
            modifier = Modifier.fillMaxWidth(),
            style = Typography.titleSmall.copy(textAlign = TextAlign.Center)
        )
        surfaces.forEach { surface ->
            RoomSurfaceCard(
                modifier = Modifier.padding(top = Paddings.DEFAULT),
                surface = surface,
                actionItems = listOf(
                    ActionItems("Редагувати", { showSurfaceEditScreen(surface) }),
                    ActionItems("Видалити", { deleteSurface(surface) })
                )
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth()
                .background(Color(0xFFA4C6E1))
                .padding(horizontal = Paddings.DEFAULT, vertical = 4.dp)
        ) {
            val area = room.surfaces.sumOf { it.area }
            val perimeter = room.surfaces.sumOf { it.perimeter }
            BodyMedium("Периметр ${perimeter.toMeasure("м")}")
            BodyMedium("Площа ${area.toMeasure("м2")}")
        }
    }
}
