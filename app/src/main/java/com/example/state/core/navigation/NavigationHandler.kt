package com.example.state.core.navigation

import androidx.navigation.NavHostController

class NavigationHandler {
    fun navigateToLogin(navController: NavHostController) {
        navController.navigate(Route.Login.route) {
            popUpTo(Route.Login.route) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun navigateToRegister(navController: NavHostController) {
        navController.navigate(Route.Register.route) {
            launchSingleTop = true
        }
    }

    fun navigateToTasks(navController: NavHostController, token: String, idUser: String) {
        navController.navigate(Route.Tasks.createRoute(token, idUser)) {
            popUpTo(Route.Login.route) { inclusive = true }
            launchSingleTop = true
        }
    }

}
