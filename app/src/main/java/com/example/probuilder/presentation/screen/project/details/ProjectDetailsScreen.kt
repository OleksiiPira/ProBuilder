package com.example.probuilder.presentation.screen.project.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.domain.model.Project
import com.example.probuilder.presentation.components.BodyMedium
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TitleLarge
import com.example.probuilder.presentation.components.TitleMedium
import com.example.probuilder.presentation.screen.categories.categories.TopBar

@Composable
fun ProjectDetailsScreen(
    bottomBar: @Composable () -> Unit = {},
    goBack: () -> Unit = {},
    viewModel: ProjectDetailsViewModel = hiltViewModel(),
) {
    val project by viewModel.project.collectAsState(Project())

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBar(
                title = project.name,
                moreActions = listOf(),
                onNavigationPress = goBack,
                onSelectAll = { /*TODO*/ },
                onSearch = { /*TODO*/ },
                isEditMode = false
            )
        }
    ) { paddings ->
        ProjectScreenContent(Modifier.padding(paddings), project)
    }
}

@Composable
fun ProjectScreenContent(
    modifier: Modifier = Modifier,
    project: Project
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        ProjectHero(project)
        TitleLarge(project.name, Modifier.padding(horizontal = Paddings.DEFAULT))
        Row(
            Modifier
                .padding(top = 4.dp, bottom = Paddings.DEFAULT)
                .padding(horizontal = Paddings.DEFAULT)
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icons.LocationSmall
            BodyMedium(project.address, fontWeight = FontWeight.Light)
        }
        UserCard(user = project.client) {}
        Column(Modifier.padding(Paddings.HorizontalPaddings)
        ) {
            PrimaryButton(text = "Сформувати кошторис", onClick = { /*TODO*/ })
            Spacer(modifier = Modifier.height(12.dp))
            SecondaryButton(text = "Сформувати фактуру", onClick = { /*TODO*/ })
        }

        Spacer(modifier = Modifier.height(20.dp))
        TitleMedium("Кімнати", Modifier.padding(horizontal = Paddings.DEFAULT))
        project.rooms.forEach { room -> RoomCard(room) }
    }

}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProjectDetailsScreenPrev() {
    ProjectScreenContent(
        Modifier,
        Project()
    )
}
