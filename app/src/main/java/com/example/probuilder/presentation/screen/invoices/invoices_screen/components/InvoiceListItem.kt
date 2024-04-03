package com.example.probuilder.presentation.screen.invoices.invoices_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun InvoiceListItem(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onEdit: () -> Unit,
    onHide: () -> Unit,
    onDelete: () -> Unit,
    name: String = "Item name",
    finalDate: String = "22.08.2024",
    totalPrice: String = "12 500",
    hidedMode: Boolean = false
) {
    var editMode by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(2f)) {
            Text(text = name, style = Typography.bodyLarge)
            Text(text = "До $finalDate", style = Typography.bodyMedium)
        }
        Text(
            modifier = Modifier.weight(1.5f),
            text = totalPrice,
            style = Typography.bodyMedium
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)
        ) {
            IconButton(onClick = { editMode = !editMode }) {
                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = editMode,
                onDismissRequest = { editMode = false }
            ) {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    },
                    text = { Text(text = "Edit") },
                    onClick = {
                        editMode = !editMode
                        onEdit()
                    })
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.RemoveRedEye,
                            contentDescription = null
                        )
                    },
                    text = { Text(text = if (hidedMode) "Show" else "Hide") },
                    onClick = {
                        editMode = !editMode
                        onHide()
                    })
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    },
                    text = { Text(text = "Delete") },
                    onClick = {
                        editMode = !editMode
                        onDelete()
                    })
            }
        }
    }
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Gray)
    )
}

@Preview
@Composable
fun PreviewInvoiceListItem() {
    InvoiceListItem(
        modifier = Modifier
            .background(Color.White)
            .width(360.dp),
        onItemClick = {},
        onHide = {},
        onDelete = {},
        onEdit = {}
    )
}
