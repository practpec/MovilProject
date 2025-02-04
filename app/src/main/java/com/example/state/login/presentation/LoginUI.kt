package com.example.state.register.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.state.register.data.model.CreateUserRequest
import com.example.state.ui.components.ButtonNavigate
import com.example.state.ui.components.CustomTextField
import com.example.state.ui.components.FontBackground
import com.example.state.ui.components.FormContainer
import com.example.state.ui.components.PrimaryButton
import com.example.state.ui.components.PrincipalText
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
@Composable
fun LoginScreen(registerViewModel: RegisterViewModel, onNavigate: (Boolean, String, String) -> Unit) {
    val username:String by registerViewModel.username.observeAsState("")
    val password:String by registerViewModel.password.observeAsState("")
    val success:Boolean by registerViewModel.success.observeAsState(false)
    val error:String by registerViewModel.error.observeAsState("")

    // Estados para la visibilidad de la contraseña
    var isPasswordVisible by remember { mutableStateOf(false) }

    val isLoggedIn = true // Cambiar según el estado real de autenticación
    val token = "sampleToken123"
    val idUser = "user123"

    FontBackground (
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier =Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ){
            PrincipalText(
                text = "¡Welcome back!"
            )

            PrincipalText(
                text = "Log in to continue",
                fontSize = 16
            )

            FormContainer {
                CustomTextField(
                    value = username,
                    onValueChange = {  registerViewModel.onChangeUsername(it)},
                    label = "Email",
                    placeholder = "Enter your email",
                    leadingIcon = Icons.Default.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    value = password,
                    onValueChange = { registerViewModel.onChangePassword(it) },
                    label = "Password",
                    placeholder = "Enter your password",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible,
                    onPasswordVisibilityChange = { isPasswordVisible = it }
                )

                Spacer(modifier = Modifier.height(24.dp))
                PrimaryButton(
                    text = "Log in",
                    onClick = { onNavigate(isLoggedIn, token, idUser) }
                )
            }
           ButtonNavigate(
               text = "Sign up here",
               onClick = { onNavigate(false, "", "") }
           )
        }

    }

}
