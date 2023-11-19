package com.example.probuilder.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.Job
import com.example.probuilder.presentation.screen.price_list_screen.components.JobPriceCard

@Composable
fun PriceListScreen(modifier: Modifier, jobs: List<Job>, onCategoryIdChanged: (String) -> Unit) {
//    var jobs by remember { mutableStateOf(listOf<Job>()) }
    Column(modifier = modifier) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            onClick = {
                onCategoryIdChanged("")
            }) {
            Text(text = "Back")
        }
        if (jobs.isNotEmpty()) {
            LazyColumn {
                items(jobs) {
                    JobPriceCard(it)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }

}