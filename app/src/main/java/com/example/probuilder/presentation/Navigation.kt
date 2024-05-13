package com.example.probuilder.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.probuilder.domain.model.Category
import com.example.probuilder.presentation.screen.home.HomeScreen
import com.example.probuilder.presentation.screen.invoices.invoices_screen.InvoicesScreen
import com.example.probuilder.presentation.screen.categories.services_screen.ServicesScreen
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreen
import com.example.probuilder.presentation.screen.categories.categories_screen.CreateCategoryOverlay
import com.example.probuilder.presentation.screen.categories.create_service.CreateServiceScreen
import com.example.probuilder.presentation.screen.categories.service_details.ServiceDetailsScreen
import com.example.probuilder.presentation.screen.invoices.invoice_details.InvoiceDetailsScreen
import com.example.probuilder.presentation.screen.invoices.create_invoice.UpsertInvoiceScreen
import com.example.probuilder.presentation.screen.profile.ProfileScreen
import com.google.gson.Gson

@Composable
fun HomeNavigation(
    navController: NavHostController,
    showActionButton: (String) -> Unit,
    setTopBarTitle: (String) -> Unit,
    bottomBar: @Composable() (() -> Unit)
) {
    NavHost(modifier = Modifier, navController = navController, startDestination = Route.HOME) {
        composable(route = Route.HOME) {
            showActionButton("")
            setTopBarTitle("Pro Builder")
            HomeScreen(
                nextScreen = navController::navigate,
                bottomBar = bottomBar
            )
        }
        composable(
            route = Route.CATEGORIES,
//            arguments = listOf(
//                navArgument("category") {
//                    type = NavType.StringType
//                    defaultValue = ""
//                },
//            )
        ) { backStackEntry ->

//            val categoryJson = navController.previousBackStackEntry?.arguments?.getString("category").orEmpty()
//            println("categoryJson = ${categoryJson}")
//
//            val category = if (categoryJson.isNotBlank()) Gson().fromJson(categoryJson, Category::class.java) else Category(id="", name = "Category1", parentId = "" )
            CategoryScreen(
                nextScreen = navController::navigate,
                bottomBar = bottomBar,
            )
        }
        navigation(
            route = Route.CATEGORIES_SECTION,
            startDestination = Route.SERVICES
        ) {


            composable(route = Route.CREATE_CATEGORY) {
                CreateCategoryOverlay(
                    onCancel = { navController.popBackStack() },
                    onSave = { navController.popBackStack() }
                )
            }
            composable(
                route = Route.SERVICES,
                arguments = listOf(
                    navArgument("categoryId") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )) {
                it.arguments?.let { it1 -> setTopBarTitle(it1.getString("categoryName", "")) }
                ServicesScreen(
                    nextScreen = navController::navigate,
                    bottomBar = bottomBar
                )
            }
            composable(
                route = Route.CREATE_SERVICE,
                arguments = listOf(
                    navArgument("category") {
                        type = NavType.StringType
                        defaultValue = ""
                    })
            ) {
                it.arguments?.let { it1 -> setTopBarTitle(it1.getString("category", "")) }
                val categoryStr = it.arguments?.getString("category")

                CreateServiceScreen(
                    nextScreen = navController::navigate,
                    bottomBar = bottomBar,
                    parentCategory = Gson().fromJson(categoryStr, Category::class.java)
                )
            }
            composable(route = Route.SERVICE_DETAILS, arguments = listOf(
                navArgument("item") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )) {
                showActionButton("")
                setTopBarTitle("Детальна інформація")
                ServiceDetailsScreen(
                    nextScreen = navController::navigate,
                    bottomBar = bottomBar
                )
            }
        }

        navigation(
            route = Route.INVOICES_SECTION,
            startDestination = Route.INVOICES
        ) {
            composable(route = Route.INVOICES) {
                showActionButton(Route.CREATE_INVOICE.replace("{invoice}", ""))
                setTopBarTitle("Список фактур")
                InvoicesScreen(
                    nextScreen = navController::navigate,
                    bottomBar = bottomBar
                )
            }
            composable(route = Route.CREATE_INVOICE, arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )) {
                showActionButton("")
                setTopBarTitle("Створити фактуру")
                UpsertInvoiceScreen(
                    nextScreen = { nextScreen ->
                        if (nextScreen == Route.BACK) {
                            navController.popBackStack()
                        } else {
                            navController.navigate(nextScreen)
                        }
                    },
                    bottomBar = bottomBar
                )
            }
            composable(route = Route.INVOICE_DETAILS, arguments = listOf(
                navArgument("invoice") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )) {
                showActionButton("")
                InvoiceDetailsScreen(
                    nextScreen = navController::navigate,
                    bottomBar = bottomBar
                )
            }
        }

        composable(
            route = Route.PROFILE,
        ) {
            Column {
                ProfileScreen()
            }
        }
        navigation(
            route = Route.PROFILE_SECTION,
            startDestination = Route.PROFILE
        ) {

        }

    }
}
