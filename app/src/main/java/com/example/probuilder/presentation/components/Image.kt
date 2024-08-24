package com.example.probuilder.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.probuilder.R

@Composable
fun Poster(imageUrl: String, height: Int, width: Int, radius: Int, contentDescription: String = "") {
    AsyncImage(
        model = imageUrl,
        modifier = Modifier.width(width.dp).height(height.dp).clip(RoundedCornerShape(radius.dp)),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.project_placeholder),
        error = painterResource(id = R.drawable.project_placeholder),
        contentDescription = contentDescription,
    )
}

@Composable
fun Poster(imageUrl: String, size: Int, radius: Int, contentDescription: String = "") {
    AsyncImage(
        model = imageUrl,
        modifier = Modifier.size(size.dp).clip(RoundedCornerShape(radius.dp)),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.project_placeholder),
        error = painterResource(id = R.drawable.project_placeholder),
        contentDescription = contentDescription,
    )
}
