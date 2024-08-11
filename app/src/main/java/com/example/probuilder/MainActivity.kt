package com.example.probuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.probuilder.presentation.HomeNavigation
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.Icons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    MainScene()
                }
            }
        }
    }
}

@Composable
fun MainScene() {
    val navController = rememberNavController()

    HomeNavigation(
        navController = navController,
        bottomBar = {
            var selectedRoute by remember { mutableStateOf("") }
            val goToRoute = { route: String ->
                selectedRoute = route
                navController.navigate(route) {
                    popUpTo(Route.HOME) { saveState = true }
                    if (route != Route.HOME) launchSingleTop = true
                }
            }
            BottomAppBar(
                containerColor = Color(0xFF2A3C48),
                contentColor = Color(0xFFFFFFFF),
                actions = {
                    BottomNavigationItem(
                        onClick = { goToRoute(Route.HOME) },
                        label = { Text(text = "Головна", maxLines = 1) },
                        icon = { Icons.Home },
                        selected = selectedRoute == Route.HOME,
                    )
                    BottomNavigationItem(
                        onClick = { goToRoute(Route.CATEGORIES) },
                        label = { Text(text = "Розцінки", maxLines = 1) },
                        icon = { Icons.Prices },
                        selected = selectedRoute == Route.CATEGORIES,
                    )
                    BottomNavigationItem(
                        onClick = { goToRoute(Route.INVOICES) },
                        label = { Text(text = "Проекти", maxLines = 1) },
                        icon = { Icons.Projects },
                        selected = selectedRoute == Route.INVOICES,
                    )
                    BottomNavigationItem(
                        onClick = { goToRoute(Route.PROFILE) },
                        label = { Text(text = "Профіль", maxLines = 1) },
                        icon = { Icons.Profile },
                        selected = selectedRoute == Route.PROFILE,
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        MainScene()
    }
}
