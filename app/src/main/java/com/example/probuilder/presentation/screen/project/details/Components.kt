package com.example.probuilder.presentation.screen.project.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
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
import com.example.probuilder.domain.model.User
import com.example.probuilder.presentation.components.BodyLarge
import com.example.probuilder.presentation.components.BodySmall
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.Poster
import com.example.probuilder.presentation.components.ProgressLarge
import com.example.probuilder.presentation.components.ProgressSmall
import com.example.probuilder.presentation.components.TitleSmall

@Composable
fun ProjectHero(project: Project) {
    Column(Modifier.padding(bottom = Paddings.DEFAULT),verticalArrangement = Arrangement.spacedBy(24.dp)) {
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
                ProgressLarge(progress = project.progress)
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

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    user: User,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() }.padding(horizontal = Paddings.DEFAULT),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Poster(imageUrl = user.imageUrl, size = 40, radius = 20, "Фото профіля")
        Column(Modifier.weight(1f)) {
            TitleSmall("Замовник:")
            BodyLarge(user.name)
        }
        IconButton(onClick) {
            Icons.ArrowRightLarge
        }
    }
}