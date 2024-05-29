package com.example.probuilder.presentation.screen.categories.categories_screen.services_section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.Service
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoriesScreenState
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent
import com.example.probuilder.presentation.screen.categories.categories_screen.ItemState
import com.example.probuilder.presentation.screen.categories.component.ServiceListItem
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun ServicesSection(
    services: List<Service>,
    screenState: CategoriesScreenState,
    onEvent: (CategoryScreenEvent) -> Unit,
    nextScreen: (String) -> Unit,
) {
    val orderedServices = mapOf(
        ItemState.FAVORITE to services.filter { it.state == ItemState.FAVORITE },
        ItemState.DEFAULT to services.filter { it.state == ItemState.DEFAULT },
        ItemState.HIDED to services.filter { it.state == ItemState.HIDED }
    )

    orderedServices.forEach { (state, services) ->
        val expandedStates = remember {
            mutableStateMapOf(
                ItemState.FAVORITE to true,
                ItemState.DEFAULT to true,
                ItemState.HIDED to false
            )
        }
        if (services.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .clickable { expandedStates[state] = !expandedStates[state]!! },
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier
                        .padding(14.dp)
                        .weight(1f),
                    text = when (state) {
                        ItemState.FAVORITE -> "Favorites items"
                        ItemState.DEFAULT -> "Default items"
                        ItemState.HIDED -> "Hided items"
                    },
                    textAlign = TextAlign.Center,
                    style = Typography.titleMedium
                )
                Icon(
                    modifier = Modifier.padding(14.dp),
                    imageVector = if (expandedStates[state] == true) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = "Expand/Collapse"
                )
            }
            HorizontalDivider(color = Color.Gray)
        }

        if (expandedStates[state] == true) {
            services.forEach {
                ServiceListItem(
                    modifier = Modifier,
                    service = it.copy(categoryName = screenState.currCategory.name),
                    onHided = { onEvent(CategoryScreenEvent.HideService(it)) },
                    onEvent = onEvent,
                    nextScreen = nextScreen
                )
                HorizontalDivider(modifier = Modifier, color = Color.Gray)
            }
        }

    }

}