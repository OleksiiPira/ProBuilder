package com.example.probuilder.presentation.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.probuilder.presentation.screen.categories.categories.TopBar

@Composable
fun ProfileScreen(
    goBack: () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    Scaffold(
        bottomBar = { bottomBar() },
        topBar = {
            TopBar(title = "Замовник", onNavigationPress = goBack, trailingIcon = {}) }
    ) { paddings ->
        Column(Modifier.padding(paddings)) {

        }
    }
}
