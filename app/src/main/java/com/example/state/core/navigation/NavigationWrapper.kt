package com.example.state.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.state.login.presentation.LoginViewModel
import com.example.state.register.presentation.RegisterViewModel
import com.example.state.tasks.presentation.TasksScreen
import com.example.state.register.presentation.LoginScreen
import com.example.state.register.presentation.RegisterScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val navHandler = NavigationHandler()

    NavHost(navController = navController, startDestination = Route.Login.route) {
        composable(Route.Login.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(loginViewModel) { isLoggedIn, token, idUser ->
                if (isLoggedIn) {
                    navHandler.navigateToTasks(navController, token, idUser)
                } else {
                    navHandler.navigateToRegister(navController)
                }
            }
        }

        composable(Route.Register.route) {
            val registerViewModel: RegisterViewModel = viewModel()
            RegisterScreen(registerViewModel) {
                navHandler.navigateToLogin(navController)
            }
        }

        composable(Route.Tasks.route) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val idUser = backStackEntry.arguments?.getString("idUser") ?: ""

            TasksScreen(
                token = token,
                idUser = idUser,
                onLogout = {
                    navHandler.navigateToLogin(navController)
                }

            )
        }



    }
}
