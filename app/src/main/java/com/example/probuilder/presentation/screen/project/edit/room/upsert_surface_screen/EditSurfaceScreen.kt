package com.example.probuilder.presentation.screen.project.edit.room.upsert_surface_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.R
import com.example.probuilder.presentation.screen.categories.categories.TopBar
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
        UpsertSurfaceContent(
            modifier = Modifier.padding(paddings),
            surface = surface,
            onEvent = viewModel::onSurfaceEvent,
            goBack = goBack
        )
    }
}

