package com.example.probuilder.presentation.screen.project.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.probuilder.R
import com.example.probuilder.domain.model.Project
import com.example.probuilder.presentation.components.BodySmall

@Composable
fun ProjectHero(project: Project) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Box {
            var textColor by remember { mutableStateOf(Color.Black) }
            AsyncImage(
                model = project.imageUrl,
                modifier = Modifier.height(240.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.project_placeholder),
                error = painterResource(id = R.drawable.project_placeholder),
                contentDescription = stringResource(R.string.description),
                onSuccess = { textColor = Color.White }
            )
            Image(modifier = Modifier
                .align(Alignment.BottomCenter)
                .alpha(0.5F), painter = painterResource(id = R.drawable.bottom_shaddow), contentDescription = null)
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                LinearProgressIndicator(
                    progress = { project.progress },
                    modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(8.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color(0xFFEEEEF2)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    BodySmall("Початок: ${project.startDate}", color = textColor, modifier = Modifier.weight(1f))
                    BodySmall("Завершення:", color = textColor)
                    BodySmall("${project.endDate}:", Color.Green)
                }
            }
        }
    }
}