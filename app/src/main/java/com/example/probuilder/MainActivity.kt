package com.example.probuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
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
import com.example.probuilder.presentation.components.HomeIcon
import com.example.probuilder.presentation.components.PricesIcon
import com.example.probuilder.presentation.components.ProfileIcon
import com.example.probuilder.presentation.components.ProjectsIcon
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.MaterialTheme.colorScheme as ThemeColors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ThemeColors.background
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
    var selectedRoute by remember { mutableStateOf(Route.HOME) }

    HomeNavigation(
        navController = navController,
        bottomBar = {
            val goToRoute = { route: String ->
                selectedRoute = route
                navController.navigate(route) {
                    popUpTo(Route.HOME) { saveState = true }
                    if (route != Route.HOME) launchSingleTop = true
                }
            }
            BottomAppBar(
                containerColor = ThemeColors.surfaceContainer,
                contentColor = Color(0xFFFFFFFF),
                actions = {
                    BottomNavigationItem(
                        onClick = { goToRoute(Route.HOME) },
                        label = { Text(text = "Головна", maxLines = 1, color = getItemColor(selectedRoute, Route.HOME)) },
                        icon = { HomeIcon(getItemColor(selectedRoute, Route.HOME)) },
                        selected = selectedRoute == Route.HOME,
                    )
                    BottomNavigationItem(
                        onClick = { goToRoute(Route.CATEGORIES) },
                        label = { Text(text = "Розцінки", maxLines = 1, color = getItemColor(selectedRoute, Route.CATEGORIES)) },
                        icon = { PricesIcon(getItemColor(selectedRoute, Route.CATEGORIES)) },
                        selected = selectedRoute == Route.CATEGORIES,
                    )
                    BottomNavigationItem(
                        onClick = { goToRoute(Route.PROJECTS_SECTION) },
                        label = { Text(text = "Проекти", maxLines = 1, color = getItemColor(selectedRoute, Route.PROJECTS_SECTION)) },
                        icon = { ProjectsIcon(getItemColor(selectedRoute, Route.PROJECTS_SECTION)) },
                        selected = selectedRoute == Route.PROJECTS_SECTION,
                    )
                    BottomNavigationItem(
                        onClick = { goToRoute(Route.PROFILE) },
                        label = { Text(text = "Профіль", maxLines = 1, color = if(selectedRoute == Route.PROFILE) ThemeColors.primary else ThemeColors.onSurface) },
                        icon = { ProfileIcon(color = if(selectedRoute == Route.PROFILE) ThemeColors.primary else ThemeColors.onSurface) },
                        selected = selectedRoute == Route.PROFILE,
                    )
                }
            )
        }
    )
}

@Composable
private fun getItemColor(selectedRoute: String, itemRoute: String): Color {
    if (selectedRoute == itemRoute) return ThemeColors.primary
    return ThemeColors.onSurface
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        MainScene()
    }
}
