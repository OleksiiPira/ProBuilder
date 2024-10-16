package com.example.probuilder.presentation.screen.project.details.client

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.R
import com.example.probuilder.domain.model.Client
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.config.Paddings
import com.example.probuilder.presentation.components.Poster
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.SimpleNote
import com.example.probuilder.presentation.components.TitleMedium
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.example.probuilder.presentation.screen.categories.component.RowItem
import com.google.gson.Gson

@Composable
fun ClientDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ClientDetailsViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit,
    nextScreen: (String) -> Unit,
    goBack: () -> Unit
) {
    val client by viewModel.client.collectAsState(initial = Client())
    val projectId by viewModel.projectId

    val showUpsertClientScreen = { nextScreen(Route.UPSERT_CLIENT.replace("{projectId}", projectId).replace("{client}", Gson().toJson(client))) }

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBar(title = "Замовник", onNavigationPress = goBack, trailingIcon = {}) }
    ) { paddings ->
        ClientDetailsScreenContent(
            modifier = modifier.padding(paddings),
            client = client,
            editClient = showUpsertClientScreen,
        )
    }
}

@Composable
private fun ClientDetailsScreenContent(
    modifier: Modifier = Modifier,
    client: Client = Client(),
    editClient: () -> Unit = {},
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier.verticalScroll(scrollState),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = Paddings.DEFAULT),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Poster(imageUrl = client.imageUrl, size = 80, radius = 40, placeHolder = R.drawable.avatar, modifier = Modifier.border(
                width = 1.dp,
                color = Color(0xFF9F9FA4),
                shape = RoundedCornerShape(40.dp)
            ))
            TitleMedium(client.name)
        }
        Spacer(modifier = Modifier.height(24.dp))
        RowItem(
            leadingIcon = { Icons.Phone },
            text = client.phone,
            onClick = {  },
            actionButton = {
                var expend by remember { mutableStateOf(false) }
                val onMoreClicked = { expend = !expend }
                DropDownButton(expend = expend, onClick = onMoreClicked) {
                    DropdownMenuItem(leadingIcon = { Icons.Delete }, text = { Text(text = "Delete") },
                        onClick = { onMoreClicked() })
                }
            }
        )
        RowItem(
            leadingIcon = { Icons.Email },
            text = client.email,
            onClick = {  },
            actionButton = {
                var expend by remember { mutableStateOf(false) }
                val onMoreClicked = { expend = !expend }
                DropDownButton(expend = expend, onClick = onMoreClicked) {
                    DropdownMenuItem(leadingIcon = { Icons.Delete }, text = { Text(text = "Delete") },
                        onClick = {
                            onMoreClicked()
                        })
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TitleMedium("Додаткова інформація", Modifier.padding(horizontal = Paddings.DEFAULT))
        SimpleNote(text = client.note)
        Spacer(modifier = Modifier.height(28.dp))
        SecondaryButton(text = "Редагувати", onClick = editClient, Modifier.padding(horizontal = Paddings.DEFAULT))
    }
}

@Preview
@Composable
fun ClientDetailsScreenContentPreview() {
    ClientDetailsScreenContent(
        modifier = Modifier.background(Color.White),
        client = Client(note = "Особисті записи. Записуйте свої думки, почуття та події, які відбуваються у вашому житті. Особисті записи. Записуйте свої думки, почуття та події, які відбуваються у вашому житті.")
    )

}
