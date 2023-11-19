package com.example.probuilder.presentation.screen.price_list_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.probuilder.domain.model.Job

@Composable
fun JobPriceCard(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(8.dp)
            .clip(RoundedCornerShape(1.dp))
            .clickable { TODO() },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
    ) {
        var isChecked by rememberSaveable { mutableStateOf(false) }
//        var resultList by rememberSaveable { mutableStateOf(mutableListOf<JobPriceSum>()) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it},
            )
            Text(
                modifier = Modifier.weight(0.8f),
                text = job.title,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = buildString {
                    append(job.price.get("Київ").toString())
                    append(" грн / ")
                    append(job.measure)
                })
        }
        if (isChecked) {
//            PriceCalculatorRow(jobPrice.)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
//        JobPriceCard(getJobPriceSample())
    }
}

//fun getJobPriceSample(): Job {
//    return Job(
//        location = "WALL",
//        category = "",
//        tags = arrayOf(""),
//        title = "Painting and Decorating",
//        price = 90,
//        currency = "грн",
//        measure = "м2"
//    )
//}