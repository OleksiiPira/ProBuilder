package com.example.probuilder.presentation.screen.project.details.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.R
import com.example.probuilder.common.ext.toJSON
import com.example.probuilder.common.ext.toMeasure
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.domain.model.Room
import com.example.probuilder.domain.model.RoomSurface
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.BodyMedium
import com.example.probuilder.presentation.components.BodySmall
import com.example.probuilder.presentation.components.CustomFloatingButton
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.config.Paddings
import com.example.probuilder.presentation.components.RoomSurfaces
import com.example.probuilder.presentation.components.TitleMedium
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.example.probuilder.presentation.screen.project.details.DetailsScreenHero
import com.example.probuilder.presentation.screen.project.details.PricesInfo

@Composable
fun RoomDetailsScreen(
    bottomBar: @Composable () -> Unit = {},
    navigateTo: (String) -> Unit,
    goBack: () -> Unit = {},
    viewModel: RoomDetailsViewModel = hiltViewModel(),
) {
    val room by viewModel.room.collectAsState(initial = Room())
    val showUpdateRoomScreen = { navigateTo(Route.UPSERT_ROOM.replace("{projectId}", viewModel.projectId).replace("{room}", room.toJSON())) }
    Scaffold(
        floatingActionButton = { CustomFloatingButton(onClick = showUpdateRoomScreen, icon = R.drawable.edit) },
        bottomBar = bottomBar,
        topBar = { TopBar(title = room.name, onNavigationPress = goBack) }
    ) { paddings ->
        RoomScreenContent(
            modifier = Modifier.padding(paddings),
            projectId = viewModel.projectId, 
            room = room,
            navigateTo = navigateTo,
            deleteSurface = viewModel::deleteSurfaces
        )
    }
}

@Composable
fun RoomScreenContent(
    modifier: Modifier = Modifier,
    projectId: String,
    room: Room,
    navigateTo: (String) -> Unit,
    deleteSurface: (RoomSurface) -> Unit
    ) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        DetailsScreenHero(room.imageUrl, room.totalHours, room.completeHours)
        PricesInfo(
            Modifier.padding(vertical = 4.dp),
            totalJobsPrice = 400023,
            totalMaterialsPrice = 400500,
            totalPrice = 800523
        )
        TitleMedium("Заміри", Modifier.padding(horizontal = Paddings.DEFAULT))
        Spacer(modifier = Modifier.height(12.dp))

        RoomSurfaces(
            room = room,
            projectId = projectId,
            navigateTo = navigateTo,
            deleteSurface = deleteSurface)

        Spacer(modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun RoomSurfaceCard(
    modifier: Modifier = Modifier,
    surface: RoomSurface,
    actionItems: List<ActionItems>,
    ) {
    Column(modifier) {
        Row(modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BodyMedium(surface.name)
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (surface.height > 0) BodySmall(text = "Висота: ${surface.height.toMeasure("м")}")
                    if (surface.width > 0) BodySmall(text = "Ширина: ${surface.width.toMeasure("м")}")
                    if (surface.length > 0) BodySmall(text = "Довжина: ${surface.length.toMeasure("м")}")
                }
            }
            var expend by remember { mutableStateOf(false) }
            val onMoreClicked = { expend = !expend }
            DropDownButton(expend = expend, onClick = onMoreClicked) {
                actionItems.forEach { item ->
                    DropdownMenuItem(leadingIcon = { item.icon }, text = { Text(text = item.text) }, onClick = {
                        onMoreClicked()
                        item.onClick()
                    })
                }
            }

        }
        surface.openings.forEach { opening ->
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .background(Color(0xFFEEEEF2))
                    .padding(start = 6.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icons.ListDot
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    BodyMedium(opening.name)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (opening.height > 0) BodySmall(
                            text = "Висота: ${
                                opening.height.toMeasure(
                                    "м"
                                )
                            }"
                        )
                        if (opening.width > 0) BodySmall(text = "Ширина: ${opening.width.toMeasure("м")}")
                        if (opening.height > 0 && opening.width > 0) BodySmall(
                            text = "Площа: ${(opening.height * opening.width).toMeasure("м2")}"
                        )
                    }
                }
            }
        }
    }
}
