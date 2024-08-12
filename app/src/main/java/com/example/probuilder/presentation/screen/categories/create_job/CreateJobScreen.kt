package com.example.probuilder.presentation.screen.categories.create_job

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.SearchItem
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.categories.categories.component.DropDownSearch
import com.example.probuilder.presentation.screen.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateJobViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit),
    onBack: () -> Unit
) {
    val newJob by viewModel.newJob
    val state by viewModel.state.collectAsState()
    val categories by viewModel.allCategories.collectAsState(emptyList())
    val selectedCategory by viewModel.selectedCategory
    val currCategory = selectedCategory
    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBar(
                title = currCategory.name,
                moreActions = listOf(ActionItems("Видалити", {  }, Icons.Delete)),
                onSelectAll = {  },
                onSearch = {},
                isEditMode = state.isEditMode) }
    ) {
        Column(
            modifier.padding(it).padding(Constants.HORIZONTAL_PADDING),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {


            TextFieldWithTitle(
                title = "Назва послуги",
                value = newJob.name,
                onValueChange = { viewModel.onEvent(CreateJobEvent.SetJobName(it)) }
            )
            DropDownSearch(
                searchTitle = "Категорія",
                currentItem = SearchItem(currCategory.name, currCategory),
                searchItems = categories.map { category ->  SearchItem(category.name, category) },
                selectItem = { searchItem -> viewModel.updateCurrentCategory(searchItem.item as Category)
                }
            )
            TextFieldWithTitle(
                title = "Одиниця виміру",
                value = newJob.measureUnit,
                onValueChange = { viewModel.onEvent(CreateJobEvent.SetMeasureUnit(it)) }
            )
            TextFieldWithTitle(
                title = "Ціна за одиницю",
                value = state.pricePerUnit,
                onValueChange = { viewModel.onEvent(CreateJobEvent.SetPricePerUnit(it)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(onClick = {
                viewModel.saveService()
                onBack()
            }) {
                Text(text = "Зберегти", style = Typography.labelLarge)
            }
            SecondaryButton(onClick = onBack) {
                Text(text = "Відмінити", style = Typography.labelLarge)
            }
        }
    }
}
