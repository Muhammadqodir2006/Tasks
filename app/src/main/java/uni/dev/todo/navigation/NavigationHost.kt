package uni.dev.todo.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uni.dev.todo.databaseLocal.AppLocalDatabase
import uni.dev.todo.screens.addUpdate.AddUpdateModel
import uni.dev.todo.screens.addUpdate.AddUpdateView
import uni.dev.todo.screens.addUpdate.AddUpdateViewModel
import uni.dev.todo.screens.home.HomeModel
import uni.dev.todo.screens.home.HomeView
import uni.dev.todo.screens.home.HomeViewModel

@Composable
fun AppNavigationHost(navController: NavHostController) {
    val context = LocalContext.current
    val appLocalDatabase = AppLocalDatabase.getInstance(context)
    val taskDao = appLocalDatabase.getTaskDao()

    val animTime = 300

    val homeModel = HomeModel(taskDao)
    val homeViewModel = HomeViewModel(navController, homeModel)

    val addUpdateModel = AddUpdateModel(taskDao)

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route,
            enterTransition = { fadeIn(tween(animTime)) },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(animTime)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(animTime)
                )
            }) {
            HomeView(homeViewModel)
        }
        composable(Screen.AddUpdate.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(animTime))
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(animTime)
                )
            }) { entry ->
            val taskId = entry.arguments?.getInt("id")!!
            val addUpdateViewModel = AddUpdateViewModel(navController, addUpdateModel, taskId)
            AddUpdateView(addUpdateViewModel)
        }
    }
}