package com.example.probuilder.presentation.screen.project.edit.client

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.R
import com.example.probuilder.domain.model.Client
import com.example.probuilder.presentation.components.LabelMedium
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.Poster
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.screen.categories.categories.TopBar

@Composable
fun UpsertClientScreen(
    viewModel: UpsertClientViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit,
    goBack: () -> Unit
) {
    val client by viewModel.client
    Scaffold(
        bottomBar = bottomBar,
        topBar = { TopBar(title = "Створити замовника", onNavigationPress = goBack) }
    ) { paddings ->
        val saveClientDetails = {
            viewModel.saveProject()
            goBack()
        }
        Column(
            modifier = Modifier.padding(paddings).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UpsertClientScreenContent(client = client, onEvent = viewModel::onEvent)
            PrimaryButton(text = "Зберегти", onClick = saveClientDetails, Modifier.padding(horizontal = Paddings.DEFAULT))
            SecondaryButton(text = "Відмінити", onClick = goBack, Modifier.padding(horizontal = Paddings.DEFAULT))
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun UpsertClientScreenContent(
    modifier: Modifier = Modifier,
    client: Client = Client(),
    onEvent: (UpsertClientEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(top = Paddings.DEFAULT),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Poster(imageUrl = client.imageUrl, size = 120.dp, radius = 60.dp, border = 1.dp, placeHolder = R.drawable.avatar)
        LabelMedium("Додати зображення", color = Color(0xFF1B2CCA), Modifier.padding(vertical = Paddings.DEFAULT))
        TextFieldWithTitle("ПІБ", client.name, { onEvent(UpsertClientEvent.SetName(it)) })
        TextFieldWithTitle("Номер телефону", client.phone, { onEvent(UpsertClientEvent.SetPhone(it)) })
        TextFieldWithTitle("Електронна адреса", client.email, { onEvent(UpsertClientEvent.SetEmail(it)) })
        TextFieldWithTitle("Додаткова інформація", client.note, { onEvent(UpsertClientEvent.SetEmail(it)) })
        Spacer(modifier = Modifier.height(16.dp))
    }
}
