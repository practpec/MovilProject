package com.example.state.core.navigation

sealed class Route(val route: String) {
    object Login : Route("login")
    object Register : Route("register")
    object Tasks : Route("tasks/{token}/{idUser}") {
        fun createRoute(token: String, idUser: String) = "tasks/$token/$idUser"
    }

}
