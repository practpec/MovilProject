package com.example.state.register.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.register.data.model.CreateUserRequest

import com.example.state.register.domain.CreateUserUSeCase
import com.example.state.register.domain.EmailValidateUseCase
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val emailValidateUseCase = EmailValidateUseCase()
    private val createUserUseCase = CreateUserUSeCase()

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean> = _success

    private var _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    fun onChangeUsername(username: String) {
        _username.value = username
    }

    fun onChangeEmail(email: String) {
        _email.value = email
    }

    fun onChangePassword(password: String) {
        _password.value = password
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun onRegisterClick(user: CreateUserRequest) {
        viewModelScope.launch {
            if (!isValidEmail(user.email)) {
                _error.value = "Correo electrónico no válido"
                return@launch
            }

            if (!isValidPassword(user.password)) {
                _error.value = "La contraseña debe tener al menos 8 caracteres"
                return@launch
            }

            try {
                val validationResult = emailValidateUseCase(user.email)

                validationResult.onSuccess { result ->
                    if (!result.success) {
                        _error.value = "El correo electrónico ya ha sido registrado"
                        return@onSuccess
                    }

                    val resultCreateUser = createUserUseCase(user)
                    resultCreateUser.onSuccess { data ->
                        _success.value = true
                        _error.value = ""
                        println("Usuario creado: ${data.id}, username: ${data.username}")
                    }.onFailure { exception ->
                        _error.value = exception.message ?: "Error desconocido"
                        println("Error al crear usuario: ${exception.message}")
                    }
                }.onFailure {
                    _error.value = "Error al validar el correo electrónico"
                    println("Error de validación de correo: ${it.message}")
                }
            } catch (exception: Exception) {
                _error.value = "Error desconocido: ${exception.message}"
                println("Error general: ${exception.message}")
            }
        }
    }
}
