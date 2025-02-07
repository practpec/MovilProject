package com.example.state.register.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.state.register.data.model.CreateUserRequest
import com.example.state.ui.components.*

@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel = viewModel(), onRegister: () -> Unit) {
    val username: String by registerViewModel.username.observeAsState("")
    val email: String by registerViewModel.email.observeAsState("")
    val password: String by registerViewModel.password.observeAsState("")
    val success: Boolean by registerViewModel.success.observeAsState(false)
    val error: String by registerViewModel.error.observeAsState("")

    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(success) {
        if (success) {
            onRegister()
        }
    }

    FontBackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            PrincipalText(text = "¡Bienvenido!")
            PrincipalText(text = "Regístrate y empieza ahora", fontSize = 16)

            FormContainer {
                CustomTextField(
                    value = username,
                    onValueChange = { registerViewModel.onChangeUsername(it) },
                    label = "Nombre de usuario",
                    placeholder = "Ingresa tu usuario",
                    leadingIcon = Icons.Default.Person
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = email,
                    onValueChange = { registerViewModel.onChangeEmail(it) },
                    label = "Correo electrónico",
                    placeholder = "Ingresa tu correo",
                    leadingIcon = Icons.Default.Email
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { registerViewModel.onChangePassword(it) },
                    label = "Contraseña",
                    placeholder = "Ingresa tu contraseña",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible,
                    onPasswordVisibilityChange = { isPasswordVisible = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Registrarse",
                    onClick = {
                        val user = CreateUserRequest(username, email, password)
                        registerViewModel.onRegisterClick(user)
                    }
                )

                if (error.isNotEmpty()) {
                    Text(text = error, color = Color.Red)
                }
            }

            ButtonNavigate(
                text = "Ir al login",
                onClick = { onRegister() }
            )
        }
    }
}