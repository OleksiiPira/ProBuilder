package com.example.probuilder.presentation.screen.project.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.probuilder.R
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.CustomFloatingButton
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun ProjectList(
    bottomBar: @Composable (() -> Unit),
    nextScreen: (String) -> Unit
    ){
    Scaffold(
        topBar = { TopBar(
            title = "Мої проекти",
            moreActions = listOf(ActionItems("Видалити", {  }, Icons.Delete)),
            onSelectAll = {  },
            onSearch = {},
            onNavigationPress = {},
            isEditMode = false) },
        floatingActionButton = { CustomFloatingButton({ nextScreen(Route.CREATE_CATEGORY.replace("{parentId}", "")) }) },
        bottomBar = bottomBar
    ) { padding ->
        Column(Modifier.padding(padding)) {
            ProjectCard()
            ProjectCard()
            ProjectCard()
            Spacer(modifier = Modifier.fillMaxHeight())
        }
    }
}

@Composable
fun ProjectCard(
    title: String = "Назва проекту проекту",
    address: String = "м. Львів, вул. Карпенка 8а, 39",
    client: String = "Іван Шевченко Андрійович",
    price: Int = 200000,
    startDate: String = "20.05.2024",
    endDate: String = "20.05.2024",
    onClick: () -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(modifier = Modifier.size(72.dp), painter = painterResource(id = R.drawable.project_placeholder), contentDescription = "")
            Column(Modifier.weight(1f)) {
                Text(text = title, style = Typography.titleSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icons.LocationSmall
                    Text(text = address, style = Typography.bodySmall)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icons.ProfileSmall
                    Text(text = client, style = Typography.bodySmall)
                }
            }
            DropDownButton(
                content = {
                    DropdownMenuItem(leadingIcon = { Icons.Delete }, text = { Text(text = "Delete") }, onClick = {  })
                })
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Вартість робіт:", style = Typography.bodyMedium, fontWeight = FontWeight.Light)
            Text(text = "$price грн", style = Typography.bodyMedium)
        }
        LinearProgressIndicator(
            progress = { 0.2F },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(8.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = Color(0xFFEEEEF2)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(modifier = Modifier.weight(1f), text = "Початок: $startDate", style = Typography.bodySmall, fontWeight = FontWeight.Light)
            Text(text = "Завершення:", style = Typography.bodySmall)
            Text(text = "$endDate:", style = Typography.bodySmall, color = Color.Green)
        }
    }
}

@Preview
@Composable
fun ProjectListPreview(){
    ProjectList(bottomBar = { /*TODO*/ }, nextScreen = {})
}