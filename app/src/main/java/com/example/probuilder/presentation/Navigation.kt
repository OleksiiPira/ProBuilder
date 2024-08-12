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
import com.example.probuilder.presentation.screen.home.HomeScreen
import com.example.probuilder.presentation.screen.invoices.invoices_screen.InvoicesScreen
import com.example.probuilder.presentation.screen.categories.jobs_screen.JobsScreen
import com.example.probuilder.presentation.screen.categories.categories.CategoryScreen
import com.example.probuilder.presentation.screen.categories.categories.create.CreateCategoryScreen
import com.example.probuilder.presentation.screen.categories.create_job.CreateJobScreen
import com.example.probuilder.presentation.screen.categories.job_details.JobDetailsScreen
import com.example.probuilder.presentation.screen.invoices.invoice_details.InvoiceDetailsScreen
import com.example.probuilder.presentation.screen.invoices.create_invoice.UpsertInvoiceScreen
import com.example.probuilder.presentation.screen.profile.ProfileScreen
import com.example.probuilder.presentation.screen.project.list.ProjectList

@Composable
fun HomeNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBar: @Composable() (() -> Unit)
) {
    NavHost(modifier = modifier, navController = navController, startDestination = Route.HOME) {
        composable(route = Route.HOME) {
            HomeScreen(
                nextScreen = navController::navigate,
                bottomBar = bottomBar
            )
        }
        composable(
            route = Route.CATEGORIES,
        ) {
            CategoryScreen(
                nextScreen = navController::navigate,
                bottomBar = bottomBar,
            )
        }
        navigation(
            route = Route.SERVICES_SECTION,
            startDestination = Route.SERVICES
        ) {
            composable(route = Route.CREATE_CATEGORY) {
                CreateCategoryScreen(onBack =  navController::popBackStack)
            }
            composable(
                route = Route.SERVICES,
                arguments = listOf(
                    navArgument("categoryId") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )) {
                JobsScreen(
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
                    },
                    navArgument("service") {
                        type = NavType.StringType
                        defaultValue = ""
                    })
            ) {
                CreateJobScreen(
                    bottomBar = bottomBar,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(route = Route.SERVICE_DETAILS, arguments = listOf(
                navArgument("item") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )) {
                JobDetailsScreen(
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
        navigation(
            route = Route.PROJECTS_SECTION,
            startDestination = Route.PROJECTS
        ) {
            composable(route = Route.PROJECTS) {
                ProjectList(bottomBar = bottomBar, nextScreen = navController::navigate)

            }
        }

    }
}
