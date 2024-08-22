package com.example.probuilder.presentation.screen.categories.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Job
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.categories.CategoriesScreenState
import com.example.probuilder.presentation.screen.ui.theme.Typography
import com.google.gson.Gson

@Composable
fun ServiceListItem(
    modifier: Modifier = Modifier,
    screenState: CategoriesScreenState,
    job: Job,
    category: Category,
    removeJob: () -> Unit,
    nextScreen: (String) -> Unit,
    paddingValue: PaddingValues
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp))
            .clickable { nextScreen(Route.SERVICE_DETAILS
                .replace("{job}", Gson().toJson(job))
                .replace("{category}", Gson().toJson(category))) }
            .padding(paddingValue),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(4.5f),
                text = job.name,
                style = Typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Column(
                modifier = Modifier.weight(1.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = job.pricePerUnit.toString(),
                    style = Typography.titleSmall
                )
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = "грн/" + job.measureUnit,
                    style = Typography.bodySmall
                )
            }

            DropDownButton(
                modifier = Modifier.weight(1f),
            ) {
                DropdownMenuItem(
                    leadingIcon = { Icons.MoreVert },
                    text = { Text(text = "Edit") },
                    onClick = { nextScreen(Route.CREATE_SERVICE
                        .replace("{categories}", Gson().toJson(screenState.currCategory))
                        .replace("{job}", Gson().toJson(job)))})
                DropdownMenuItem(
                    leadingIcon = { Icons.Delete },
                    text = { Text(text = "Delete") },
                    onClick = removeJob)
            }
        }
    }
}

@Composable
fun DropDownButton(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    var expend by remember { mutableStateOf(false) }
    val onMoreClicked = { expend = !expend }
    Box(modifier = modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onMoreClicked) { Icons.MoreVert }
        DropdownMenu(
            modifier = Modifier.widthIn(min = 160.dp).padding(end = 8.dp),
            expanded = expend,
            onDismissRequest = { expend = false }
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        ServiceListItem(
            screenState = CategoriesScreenState(),
            job = Job(name = "Painting and Decorating", pricePerUnit = 90, measureUnit = "м2"),
            category = Category(name = "TestCategory"),
            removeJob = {},
            nextScreen = System.out::println,
            paddingValue = PaddingValues()
        )
    }
}
