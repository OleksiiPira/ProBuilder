package com.example.probuilder.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.probuilder.domain.model.Category

@Composable
fun CategoryScreen(modifier: Modifier, feed: List<Category>, onCategoryIdChanged: (String) -> Unit){
    Column(modifier = modifier) {
        var categories: List<Category> = feed

        LazyColumn {
            items(categories) {
                TextButton(
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                    onClick = {
                        onCategoryIdChanged(it.id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        tint = Color.Black,
                        contentDescription = "Localized description"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = it.title,
                        fontSize = 17.sp, // Set the text size to 17 scaled pixels
                        fontFamily = FontFamily.Serif, // Replace R.font.kumbh_sans with your font resource
                        fontWeight = FontWeight.Medium, // Set the font weight to medium
                        color = Color.Black, // Set the text color
                    )
                    Spacer(modifier = Modifier.width(intrinsicSize = IntrinsicSize.Max))
                }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}
