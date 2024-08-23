package com.example.probuilder.presentation.screen.project.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.screen.categories.categories.TopBar

@Composable
fun CreateProjectScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateProjectViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit,
    onBack: () -> Unit
) {
    val project by viewModel.project
    val scrollState = rememberScrollState()
    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBar(
                title = "Створити проєкт",
                moreActions = listOf(ActionItems("Видалити", {  }, Icons.Delete)),
                onSelectAll = {  },
                onSearch = {},
                onNavigationPress = onBack,
                isEditMode = false) }
    ) { paddings ->
        Column(
            modifier
                .padding(paddings)
                .padding(Constants.HORIZONTAL_PADDING)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {
            TextFieldWithTitle(
                title = "Назва роботи",
                value = project.name,
                onValueChange = { viewModel.onEvent(CreateProjectEvent.SetName(it)) }
            )
            TextFieldWithTitle(
                title = "Адреса",
                value = project.address,
                onValueChange = { viewModel.onEvent(CreateProjectEvent.SetAddress(it)) }
            )
            TextFieldWithTitle(
                title = "Клієнт",
                value = project.clientName,
                onValueChange = { viewModel.onEvent(CreateProjectEvent.SetClientName(it)) }
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextFieldWithTitle(
                    modifier = Modifier.weight(1f),
                    title = "Початок",
                    value = project.startDate,
                    onValueChange = { viewModel.onEvent(CreateProjectEvent.SetStartDate(it)) }
                )
                TextFieldWithTitle(
                    modifier = Modifier.weight(1f),
                    title = "Кінець",
                    value = project.endDate,
                    onValueChange = { viewModel.onEvent(CreateProjectEvent.SetEndDate(it)) }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextFieldWithTitle(
                    modifier = Modifier.weight(1f),
                    title = "Ціна",
                    value = project.price.toString(),
                    onValueChange = {
                        viewModel.onEvent(
                            CreateProjectEvent.SetPrice(
                                it.toIntOrNull() ?: 0
                            )
                        )
                    }
                )

                TextFieldWithTitle(
                    modifier = Modifier.weight(1f),
                    title = "Прогрес",
                    value = project.progress.toString(),
                    onValueChange = {
                        viewModel.onEvent(
                            CreateProjectEvent.SetProgress(
                                it.toFloatOrNull() ?: 0F
                            )
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(text = "Зберегти", onClick = {
                viewModel.saveProject()
                onBack()
            })
            SecondaryButton(text = "Відмінити", onClick = onBack)
        }
    }
}