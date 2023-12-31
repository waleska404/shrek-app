package com.waleska404.shrek.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.waleska404.shrek.ui.screens.details.DetailsScreen
import com.waleska404.shrek.ui.screens.home.HomeScreen
import com.waleska404.shrek.ui.screens.search.SearchScreen
import com.waleska404.shrek.ui.screens.splash.SplashScreen
import com.waleska404.shrek.ui.screens.welcome.WelcomeScreen
import com.waleska404.shrek.util.Constants.DETAILS_ARGUMENT_KEY

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailsScreen(navController = navController)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(
                navController = navController
            )
        }
    }
}