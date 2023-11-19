package com.example.probuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.AppTheme
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.PriceList
import com.example.probuilder.presentation.FeedViewModel
import com.example.probuilder.presentation.screen.CategoryScreen
import com.example.probuilder.presentation.screen.PriceListScreen
import com.example.probuilder.presentation.screen.home_screen.HomeScreenContent
import com.example.probuilder.presentation.screen.home_screen.components.TextIconButton
import com.example.probuilder.presentation.screen.price_list_screen.JobsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScene()
//                    CenterAlignedTopAppBarExample()
//                    JobsScreen(viewModel = viewModel())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScene() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var selectedButtonId by  remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = {
                    Text("ProBuilder")
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                actions = {

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextIconButton(iconImageVector = Icons.Outlined.Home, text = "Головна") {
                            selectedButtonId = "Home"
                        }
                        TextIconButton(
                            iconImageVector = Icons.Outlined.List,
                            text = "Розцінки"
                        ) { selectedButtonId = "PriceList" }
                        IconButton(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape),
                            onClick = { /* do something */ },
                        ) {
                            Icon(
                                modifier = Modifier.size(46.dp),
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Створити"
                            )
                        }
                        TextIconButton(
                            iconImageVector = Icons.Outlined.ReceiptLong,
                            text = "Фактура"
                        ) { selectedButtonId = "Home" }
                        TextIconButton(
                            iconImageVector = Icons.Outlined.Person,
                            text = "Профіль"
                        ) { selectedButtonId = "Home" }
                    }
                }
            )
        }
    ) { innerPadding ->
        val feedViewModel = FeedViewModel()

        val modifier = Modifier.padding(innerPadding)
        if(selectedButtonId == "Home") {
            HomeScreenContent(modifier = modifier)
        } else if (selectedButtonId == "PriceList") {
//            JobsScreen(modifier = Modifier.padding(innerPadding), viewModel())
            val feed by feedViewModel.priceList.observeAsState()
            var resultFeed = feed ?: PriceList(listOf(Category()))

            var currCategoryId by remember { mutableStateOf("") }
            if (currCategoryId.isEmpty()) {
                CategoryScreen(modifier = modifier, resultFeed.categories) { str -> currCategoryId = str }
            } else {
                var jobs = feedViewModel.getJobs(currCategoryId)
                PriceListScreen(modifier = modifier, jobs) { str: String -> currCategoryId = str }
            }
        } else {

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBarExample() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = {
                    Text("ProBuilder")
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
//        ScrollContent(innerPadding)

        JobsScreen(modifier = Modifier.padding(innerPadding), viewModel = viewModel())
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
//        Greeting("Android")
    }
}