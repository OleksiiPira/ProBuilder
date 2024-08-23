package com.example.probuilder.presentation.screen.project.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TitleLarge
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
    Column(modifier) {
        ProjectHero(project)
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column {
                TitleLarge(project.name)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icons.LocationSmall
                    BodyMedium(project.address, fontWeight = FontWeight.Light)
                }
            }
            PrimaryButton(text = "Сформувати кошторис", onClick = { /*TODO*/ })
            SecondaryButton(text = "Сформувати фактуру", onClick = { /*TODO*/ })
        }
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
