package com.example.probuilder.presentation.screen.project.edit.room.upsert_surface_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.R
import com.example.probuilder.presentation.components.FixedButtonBackground
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.project.edit.room.UpsertSurfaceEvent
import com.example.probuilder.presentation.screen.project.edit.room.upsert_content.UpsertSurfaceContent


@Composable
fun EditSurfaceScreen(
    viewModel: EditSurfaceViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    Scaffold(
        topBar = { TopBar(title = stringResource(R.string.add_measure_btn), onNavigationPress = goBack) }
    ) { paddings ->
        val surface by viewModel.surface
        Box(Modifier.padding(paddings).fillMaxSize()) {
            UpsertSurfaceContent(
                modifier = Modifier,
                surface = surface,
                onEvent = viewModel::onSurfaceEvent,
            )
            FixedButtonBackground(Modifier.align(Alignment.BottomCenter)){
                PrimaryButton(
                    text = stringResource(id = R.string.save_btn),
                    onClick = { viewModel.onSurfaceEvent(UpsertSurfaceEvent.Save); goBack() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = Paddings.DEFAULT)
                )
            }
        }
    }
}
