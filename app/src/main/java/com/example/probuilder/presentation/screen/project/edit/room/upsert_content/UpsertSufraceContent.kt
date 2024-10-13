package com.example.probuilder.presentation.screen.project.edit.room.upsert_content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.probuilder.R
import com.example.probuilder.domain.model.RoomSurface
import com.example.probuilder.domain.model.SurfaceOption
import com.example.probuilder.domain.model.SurfaceType
import com.example.probuilder.presentation.components.BodyLarge
import com.example.probuilder.presentation.components.DialogOptions
import com.example.probuilder.presentation.components.FrameButton
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.NumericTextFieldWithTitle
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.components.TitleMedium
import com.example.probuilder.presentation.screen.project.edit.room.UpsertSurfaceEvent

@Composable
fun UpsertSurfaceContent(
    modifier: Modifier = Modifier,
    surface: RoomSurface = RoomSurface(),
    onEvent: (UpsertSurfaceEvent) -> Unit,
) {
    val setName = { name: String -> onEvent(UpsertSurfaceEvent.SetName(name)) }
    val setType = { type: SurfaceType -> onEvent(UpsertSurfaceEvent.SetType(type)) }
    val setHeight = { height: Double -> onEvent(UpsertSurfaceEvent.SetHeight(height)) }
    val setWidth = { width: Double -> onEvent(UpsertSurfaceEvent.SetWidth(width)) }
    val setLength = { length: Double -> onEvent(UpsertSurfaceEvent.SetLength(length)) }
    val setDepth = { depth: Double -> onEvent(UpsertSurfaceEvent.SetDepth(depth)) }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Paddings.DEFAULT)
            .padding(vertical = Paddings.DEFAULT)
            .padding(WindowInsets.ime.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(Paddings.DEFAULT)
    ) {
        TextFieldWithTitle(stringResource(R.string.name_title), surface.name, setName)
        Column(Modifier.padding(horizontal = Paddings.DEFAULT)) {
            var showOptionsDialog by remember { mutableStateOf(false) }
            val surfaceTypes = listOf(SurfaceType.WALL, SurfaceType.FLOOR, SurfaceType.CEILING, SurfaceType.OTHER)
            val dismiss = { showOptionsDialog = !showOptionsDialog }

            BodyLarge(stringResource(R.string.surface_title), modifier = Modifier.padding(bottom = 4.dp))

            FrameButton(onClick = dismiss) {
                BodyLarge(surface.type.label, modifier = Modifier.weight(1f))
                Icons.ArrowDown
            }

            if (showOptionsDialog) DialogOptions(
                options = surfaceTypes,
                dismiss = dismiss,
                onOptionSelected = { setType(it as SurfaceType) }
            )
        }


        Row(
            modifier = Modifier.padding(horizontal = Paddings.DEFAULT),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (surface.type) {
                SurfaceType.WALL, SurfaceType.OTHER -> NumericTextFieldWithTitle(
                    modifier = Modifier.weight(1f),
                    title = SurfaceOption.HEIGHT.label,
                    value = surface.height,
                    onValueChange = setHeight,
                    horizPaddings = Paddings.EMPTY
                )

                SurfaceType.CEILING, SurfaceType.FLOOR -> NumericTextFieldWithTitle(
                    modifier = Modifier.weight(1f),
                    title = SurfaceOption.LENGTH.label,
                    value = surface.length,
                    onValueChange = setLength,
                    horizPaddings = Paddings.EMPTY
                )
            }
            NumericTextFieldWithTitle(
                modifier = Modifier.weight(1f),
                title = SurfaceOption.WIDTH.label,
                value = surface.width,
                onValueChange = setWidth,
                horizPaddings = Paddings.EMPTY
            )
        }

        if (surface.type == SurfaceType.OTHER) {
            Row(
                modifier = Modifier.padding(horizontal = Paddings.DEFAULT),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NumericTextFieldWithTitle(
                    modifier = Modifier.weight(1f),
                    title = SurfaceOption.LENGTH.label,
                    value = surface.length,
                    onValueChange = setLength,
                    horizPaddings = Paddings.EMPTY
                )
                NumericTextFieldWithTitle(
                    modifier = Modifier.weight(1f),
                    title = SurfaceOption.DEPTH.label,
                    value = surface.depth,
                    onValueChange = setDepth,
                    horizPaddings = Paddings.EMPTY
                )
            }
        }

        if (surface.type != SurfaceType.OTHER) MeasureResultSection(surface)
    }
}

@Composable
fun MeasureResultSection(
    surface: RoomSurface
) {
    Column(
        modifier = Modifier
            .padding(top = Paddings.DEFAULT)
            .background(Color(0xFFEEEEF2))
            .padding(vertical = 12.dp, horizontal = Paddings.DEFAULT),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleMedium("Результат", modifier = Modifier.padding(bottom = 12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumericTextFieldWithTitle(
                title = SurfaceOption.AREA.label,
                value = surface.area,
                horizPaddings = Paddings.EMPTY,
                modifier = Modifier.weight(1f)
            )

            if (surface.type != SurfaceType.WALL) {
                NumericTextFieldWithTitle(
                    title = SurfaceOption.PERIMETER.label,
                    value = surface.perimeter,
                    horizPaddings = Paddings.EMPTY,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
