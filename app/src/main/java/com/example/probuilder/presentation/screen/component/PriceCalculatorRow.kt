package com.example.probuilder.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceCalculatorRow(
    price: Int = 3,
//    viewModel: JobPriceSumViewModel
//    measure: Double,
//    onMeasureChange: (Double) -> Unit,
//    sum: Double,
//    onSumChange: (Double) -> Unit
) {
    var location by rememberSaveable { mutableStateOf("") }
    var measure by rememberSaveable { mutableStateOf("") }
    var sum by rememberSaveable { mutableStateOf(0.0) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(2f),
            value = location,
            onValueChange = { location = it },
            placeholder = { Text(text = "Спальня") }
        )
        Spacer(modifier = Modifier.width(9.dp))
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = measure,
            onValueChange = {
                var measureDouble = if(it.isNotEmpty()) it.toDouble() else 0.0
                measure = measureDouble.toString()
                sum = measureDouble * price
//                viewModel.addItem(JobPriceSum())
            },
            placeholder = { Text(text = "0.0 м2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(modifier = Modifier.width(9.dp))
        Text(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            text =  sum.toString()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PriceCalculatorRowPreview() {
    AppTheme {
//        JobPriceCard(getJobPriceSample())
    }
}