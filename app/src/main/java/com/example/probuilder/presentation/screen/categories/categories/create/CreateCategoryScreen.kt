package com.example.probuilder.presentation.screen.categories.categories.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.domain.model.ButtonCfg
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.screen.categories.categories.TopBar

@Composable
fun CreateCategoryScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    bottomBar: @Composable () -> Unit,
    viewModel: CreateCategoryViewModel = hiltViewModel(),
) {
    var categoryName by rememberSaveable { mutableStateOf("") }

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBar(
                title = "Створити категорію",
                moreActions = listOf(ActionItems("Видалити", {  }, Icons.Delete)),
                onSelectAll = {  },
                onSearch = {},
                onNavigationPress = onBack,
                isEditMode = false) }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .clip(ButtonCfg.RoundedShape)
                .background(Color.White)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val onSave = {
                viewModel.createCategory(categoryName)
                onBack()
            }

            TextFieldWithTitle(
                title = "Назва категорії",
                value = categoryName,
                onValueChange = { categoryName = it })

            PrimaryButton(text = "Зберегти", onClick = onSave)
            SecondaryButton(text = "Відмінити", onClick = onBack)
        }

    }
}