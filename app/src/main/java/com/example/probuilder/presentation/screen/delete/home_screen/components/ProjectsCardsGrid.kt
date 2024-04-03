package com.example.probuilder.presentation.screen.delete.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.probuilder.domain.model.delete.Project

@Composable
fun ProjectCard(project: Project, onProjectSelected: (Project) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF2C96B1),
                            Color(0xFF22A6B4),
                            Color(0xFF86C6E8)
                        )
                    )
                )
                .padding(
                    horizontal = 8.dp, vertical = 10.dp
                )
                .clickable {
                    onProjectSelected(project)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = Icons.Filled.House,
                    contentDescription = "Будинок",
                    tint = MaterialTheme.colorScheme.surface
                )
                Text(
                    text = project.address,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.surface,
                    maxLines = 2
                )
                Text(
                    modifier = Modifier.clickable { },
                    fontWeight = FontWeight.Bold,
                    text = ":",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.surface
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {

//                Text(
//                    text = "Власник: ",
//                    color = MaterialTheme.colorScheme.surface,
//                    style = MaterialTheme.typography.bodySmall,
//                )
                Icon(
                    modifier = Modifier.size(15.dp),
                    imageVector = Icons.Outlined.AccountBox,
                    contentDescription = "Будинок",
                    tint = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = project.owner,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                    )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                text = project.date,
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.surface,
            )
        }

    }
}

//@Preview
//@Composable
//fun Crd() {
//    ProjectCard(project = Project("вул.Тракт глинянський 89б", "20 квітня, 2023", "Піра Олексій"))
//}
