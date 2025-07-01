package com.williamv.debtmake.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.williamv.debtmake.ui.home.HomeScreen
import com.williamv.debtmake.ui.details.DetailsScreen

object Routes {
    const val HOME = "home"
    const val DETAILS = "details"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        // Home
        composable(Routes.HOME) {
            HomeScreen { id ->
                navController.navigate("${Routes.DETAILS}/$id")
            }
        }

        // Details，声明带参数的 route
        composable(
            route = "${Routes.DETAILS}/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            DetailsScreen(itemId = itemId)
        }
    }
}
