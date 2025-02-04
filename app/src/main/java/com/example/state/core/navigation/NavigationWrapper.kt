package com.example.state.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.state.create.presentation.CreateScreen
import com.example.state.edit.presentation.EditScreen
import com.example.state.register.presentation.LoginScreen
import com.example.state.register.presentation.RegisterScreen
import com.example.state.tasks.presentation.TasksScreen
import com.example.state.register.presentation.RegisterViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        // Login Screen
        composable("login") {
            LoginScreen(RegisterViewModel()) { isLoggedIn, token, idUser ->
                if (isLoggedIn) {
                    navController.navigate("tasks/$token/$idUser") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                } else {
                    navController.navigate("register"){
                        launchSingleTop = true
                    }
                }
            }
        }

        // Register Screen
        composable("register") {
            RegisterScreen(RegisterViewModel()) {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                    launchSingleTop = true
                }
            }
        }

        // Tasks Screen
        composable("tasks/{token}/{idUser}") { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val idUser = backStackEntry.arguments?.getString("idUser") ?: ""
            TasksScreen(
                token = token,
                idUser = idUser,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onCreateTask = {
                    navController.navigate("create/$idUser/$token")
                },
                onEditTask = { taskId ->
                    navController.navigate("edit/$taskId/$idUser/$token")
                }
            )
        }

        // Create Task Screen
        composable("create/{idUser}/{token}") { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val idUser = backStackEntry.arguments?.getString("idUser") ?: ""
            CreateScreen(
                idUser = idUser,
                token = token,
                onTaskCreated = {
                    // Navega a Tasks limpiando el stack para que la pantalla de creación no quede en el back stack.
                    navController.navigate("tasks/$token/$idUser") {
                        popUpTo("create/$idUser/$token") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Edit Task Screen
        composable("edit/{taskId}/{idUser}/{token}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val idUser = backStackEntry.arguments?.getString("idUser") ?: ""
            EditScreen(
                taskId = taskId,
                token = token,
                idUser = idUser,
                onTaskEdited = {
                    // Navega a Tasks limpiando el stack para que la pantalla de edición no quede en el back stack.
                    navController.navigate("tasks/$token/$idUser") {
                        popUpTo("edit/$taskId/$idUser/$token") { inclusive = true }
                        launchSingleTop = true
                    }
                })
        }
    }
}