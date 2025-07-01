package com.williamv.debtmake.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.williamv.debtmake.ui.details.DetailsScreen
import com.williamv.debtmake.ui.home.HomeScreen

object Routes {
    const val HOME = "home"
    const val DETAILS = "details"
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier       // ← 新增 modifier 参数
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier              // ← 传给 NavHost
    ) {
        // Home
        composable(Routes.HOME) {
            HomeScreen { id ->
                navController.navigate("${Routes.DETAILS}/$id")
            }
        }

        // Details（带参数的 route）
        composable(
            route = "${Routes.DETAILS}/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            DetailsScreen(itemId = itemId)
        }
    }
}
