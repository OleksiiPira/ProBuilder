package com.example.probuilder.presentation.screen.project.create

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.probuilder.R
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.LabelMedium
import com.example.probuilder.presentation.components.Paddings
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
        bottomBar = {
            Column(modifier = Modifier.padding(horizontal = Paddings.DEFAULT).padding(bottom = 40.dp),) {
                Row(horizontalArrangement = Arrangement.spacedBy(168.dp)) {
                    SecondaryButton(text = "Назад", onClick = onBack, modifier.weight(1f))
                    PrimaryButton(text = "Далі", onClick = {
                        viewModel.saveProject()
                        onBack()
                    }, modifier.weight(1f))
                }
            }
        },
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
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {
            AsyncImage(
                model = project.imageUrl,
                modifier = Modifier.height(170.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.project_placeholder),
                error = painterResource(id = R.drawable.project_placeholder),
                contentDescription = stringResource(R.string.description),
                onSuccess = {  }
            )
            Row(Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.Center) {
                LabelMedium("Додати зображення", color = Color(0xFF1B2CCA))
            }
            TextFieldWithTitle(
                title = "Назва роботи",
                value = project.name,
                onValueChange = { viewModel.onEvent(CreateProjectEvent.SetName(it)) },
                modifier = Modifier.padding(horizontal = Paddings.DEFAULT)
            )
            TextFieldWithTitle(
                title = "Адреса",
                value = project.address,
                onValueChange = { viewModel.onEvent(CreateProjectEvent.SetAddress(it)) },
                modifier = Modifier.padding(horizontal = Paddings.DEFAULT)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = Paddings.DEFAULT)
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
            Spacer(Modifier.height(16.dp))
        }
    }
}