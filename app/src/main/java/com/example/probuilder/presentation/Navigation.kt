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
import com.example.probuilder.presentation.screen.categories.categories.CategoryScreen
import com.example.probuilder.presentation.screen.categories.categories.create.CreateCategoryScreen
import com.example.probuilder.presentation.screen.home.HomeScreen
import com.example.probuilder.presentation.screen.invoices.create_invoice.UpsertInvoiceScreen
import com.example.probuilder.presentation.screen.invoices.invoice_details.InvoiceDetailsScreen
import com.example.probuilder.presentation.screen.invoices.invoices_screen.InvoicesScreen
import com.example.probuilder.presentation.screen.job.create.CreateJobScreen
import com.example.probuilder.presentation.screen.job.list.JobsScreen
import com.example.probuilder.presentation.screen.profile.ProfileScreen
import com.example.probuilder.presentation.screen.project.create.CreateProjectScreen
import com.example.probuilder.presentation.screen.project.details.ProjectDetailsScreen
import com.example.probuilder.presentation.screen.project.details.client.ClientDetailsScreen
import com.example.probuilder.presentation.screen.project.details.room.RoomDetailsScreen
import com.example.probuilder.presentation.screen.project.details.worker.WorkerDetailsScreen
import com.example.probuilder.presentation.screen.project.edit.client.UpsertClientScreen
import com.example.probuilder.presentation.screen.project.edit.worker.UpsertWorkerScreen
import com.example.probuilder.presentation.screen.project.list.ProjectList

@Composable
fun HomeNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBar: @Composable () -> Unit
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
                goBack = navController::popBackStack
            )
        }
        navigation(
            route = Route.SERVICES_SECTION,
            startDestination = Route.SERVICES
        ) {
            composable(route = Route.CREATE_CATEGORY) {
                CreateCategoryScreen(
                    onBack = navController::popBackStack,
                    bottomBar = bottomBar)
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
                    bottomBar = bottomBar,
                    goBack = navController::popBackStack
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
                ProfileScreen(goBack = navController::popBackStack, bottomBar = bottomBar)
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
            composable(route = Route.CREATE_PROJECTS) {
                CreateProjectScreen(goBack = navController::popBackStack)
            }
            composable(route = Route.PROJECT_DETAILS) {
                ProjectDetailsScreen(
                    bottomBar = bottomBar,
                    nextScreen = navController::navigate,
                    goBack = navController::popBackStack,
                )
            }

            composable(Route.CLIENT_DETAILS) {
                ClientDetailsScreen(bottomBar = bottomBar, nextScreen = navController::navigate, goBack = navController::popBackStack)
            }
            composable(Route.UPSERT_CLIENT) {
                UpsertClientScreen(bottomBar = bottomBar, goBack = navController::popBackStack)
            }

            composable(Route.WORKER_DETAILS) {
                WorkerDetailsScreen(bottomBar = bottomBar, nextScreen = navController::navigate, goBack = navController::popBackStack)
            }
            composable(Route.UPSERT_WORKER) {
                UpsertWorkerScreen(bottomBar = bottomBar, goBack = navController::popBackStack)
            }
            composable(Route.ROOM_DETAILS) {
                RoomDetailsScreen(bottomBar = bottomBar, nextScreen = navController::navigate, goBack = navController::popBackStack)
            }
        }
    }
}
