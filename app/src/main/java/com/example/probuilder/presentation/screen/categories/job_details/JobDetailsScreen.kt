package com.example.probuilder.presentation.screen.categories.job_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants
import com.example.probuilder.presentation.components.ColumnListItem
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun JobDetailsScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    goBack: () -> Unit,
    viewModel: JobDetailsViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit
) {
    val service by viewModel.jobState.collectAsState()
    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBar(
                title = service.name,
                moreActions = listOf(),
                onNavigationPress = goBack,
                onSelectAll = { /*TODO*/ },
                onSearch = { /*TODO*/ },
                isEditMode = false
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(Constants.HORIZONTAL_PADDING),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = service.name,
                style = Typography.titleLarge
            )
            ColumnListItem(title = "Категорія", value = "ServiceDetailsScreen")
            ColumnListItem(title = "Одиниця виміру", value = service.measureUnit)
            ColumnListItem(title = "Ціна", value = service.pricePerUnit.toString())
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            PrimaryButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Додати до фактури",
                    style = Typography.labelLarge
                )
            }
            SecondaryButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Приховати",
                    style = Typography.labelLarge
                )
            }
            SecondaryButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Видалити",
                    style = Typography.labelLarge
                )
            }
        }
    }
}