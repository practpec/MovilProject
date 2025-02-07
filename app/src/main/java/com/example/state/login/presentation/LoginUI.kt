package com.example.state.register.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.state.login.data.model.LoginRequest
import com.example.state.login.presentation.LoginViewModel
import com.example.state.ui.components.ButtonNavigate
import com.example.state.ui.components.CustomTextField
import com.example.state.ui.components.FontBackground
import com.example.state.ui.components.FormContainer
import com.example.state.ui.components.PrimaryButton
import com.example.state.ui.components.PrincipalText

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigate: (Boolean, String, String) -> Unit // Pasamos el login status y los valores
) {
    val email: String by loginViewModel.email.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val success: Boolean by loginViewModel.success.observeAsState(false)
    val error: String by loginViewModel.error.observeAsState("")

    // Obtener token e idUser desde el ViewModel
    val token: String by loginViewModel.token.observeAsState("")
    val idUser: String by loginViewModel.idUser.observeAsState("")

    // Estado para la visibilidad de la contraseña
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Si el login es exitoso, pasamos el token y el idUser
    if (success) {
        onNavigate(true, token, idUser)
    }

    FontBackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            PrincipalText(text = "¡Bienvenido de nuevo!")
            PrincipalText(text = "Inicia sesión para continuar", fontSize = 16)

            FormContainer {
                CustomTextField(
                    value = email,
                    onValueChange = { loginViewModel.onChangeEmail(it) },
                    label = "Correo electrónico",
                    placeholder = "Ingresa tu correo",
                    leadingIcon = Icons.Default.Email
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { loginViewModel.onChangePassword(it) },
                    label = "Contraseña",
                    placeholder = "Ingresa tu contraseña",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible,
                    onPasswordVisibilityChange = { isPasswordVisible = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Iniciar sesión",
                    onClick = {
                        val user = LoginRequest(email, password)
                        loginViewModel.onLoginClick(user)
                    }
                )

                if (error.isNotEmpty()) {
                    Text(text = error, color = Color.Red)
                }
            }

            ButtonNavigate(
                text = "¿No tienes cuenta? Regístrate aquí",
                onClick = { onNavigate(false, "", "") }
            )
        }
    }
}


