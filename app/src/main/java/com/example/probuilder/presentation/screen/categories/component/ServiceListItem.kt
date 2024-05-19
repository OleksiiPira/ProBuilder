package com.example.probuilder.presentation.screen.categories.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.probuilder.domain.model.Service
import com.example.probuilder.presentation.Route
import com.google.gson.Gson

@Composable
fun ServiceListItem(
    modifier: Modifier = Modifier,
    service: Service,
    onSelected: (String) -> Unit,
    onHided: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp))
            .clickable {
                val jsonJob = Gson().toJson(service)
                onSelected(Route.SERVICE_DETAILS.replace("{item}", jsonJob))
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(3f),
                text = service.name,
                color = MaterialTheme.colorScheme.onSurface
            )
            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = service.pricePerUnit.toString() + " грн"
                )
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = service.measure
                )
            }

            DropDownButton(
                modifier = Modifier.weight(0.5f),
            ) {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    },
                    text = { Text(text = "Edit") },
                    onClick = {})
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.RemoveRedEye,
                            contentDescription = null
                        )
                    },
                    text = { Text(text = "Hide") },
                    onClick = {
                        onHided()
                    })
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    },
                    text = { Text(text = "Delete") },
                    onClick = {  })
            }
        }
    }
}

@Composable
fun DropDownButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.MoreVert,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    var editMode by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { editMode = !editMode }) {
            Icon(imageVector = icon, contentDescription = null)
        }
        DropdownMenu(
            modifier = Modifier.widthIn(min = 160.dp).padding(end = 8.dp),
            expanded = editMode,
            onDismissRequest = { editMode = false }
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
            service = Service(
                id = "",
                categoryId = "",
                name = "Painting and Decorating",
                pricePerUnit = 90,
                measure = "м2"
            ),
            onSelected = System.out::println,
            onHided = {})
    }
}
