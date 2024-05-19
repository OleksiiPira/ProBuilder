package com.example.probuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.probuilder.presentation.HomeNavigation
import com.example.probuilder.presentation.Route
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
    var topBarTitle by remember { mutableStateOf("") }
    val navController = rememberNavController()
    var actionButtonRoute by rememberSaveable { mutableStateOf("") }

    HomeNavigation(
        navController = navController,
        showActionButton = { actionButtonRoute = it },
        setTopBarTitle = { topBarTitle = it },
        bottomBar = {
            var selectedIndex by remember { mutableStateOf(0) }
            BottomAppBar(
                containerColor = Color.LightGray.copy(alpha = 0.2f),
                actions = {
                    listOf(
                        BottomBarItem("Головна", Icons.Outlined.Home, Route.HOME),
                        BottomBarItem("Розцінки", Icons.Outlined.List, Route.CATEGORIES),
                        BottomBarItem("Фактура", Icons.Outlined.ReceiptLong, Route.INVOICES_SECTION),
                        BottomBarItem("Профіль", Icons.Outlined.Person, Route.PROFILE)
                    ).forEachIndexed { index, it ->
                        BottomNavigationItem(
                            enabled = true,
                            onClick = {
                                selectedIndex = index
                                navController.navigate(it.route) {
                                    popUpTo(Route.HOME) { saveState = true }
                                    if (selectedIndex != 1) launchSingleTop = true
//                                    restoreState = true
                                }
                            },
                            label = { Text(text = it.title) },
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = it.title
                                )
                            },
                            selected = selectedIndex == index,
                        )
                    }
                }
            )
        }
    )
}

data class BottomBarItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
//        Greeting("Android")
    }
}
