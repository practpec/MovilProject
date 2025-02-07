package com.example.state.login.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.login.data.model.LoginRequest
import com.example.state.login.data.model.LoginResponse
import com.example.state.login.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginUseCase = LoginUseCase()

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean> = _success

    private var _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    private var _token = MutableLiveData<String>("")
    val token: LiveData<String> = _token

    private var _idUser = MutableLiveData<String>("")
    val idUser: LiveData<String> = _idUser

    fun onChangeEmail(email: String) {
        _email.value = email
    }

    fun onChangePassword(password: String) {
        _password.value = password
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onLoginClick(user: LoginRequest): LiveData<LoginResponse> {
        val loginResponse = MutableLiveData<LoginResponse>()
        viewModelScope.launch {
            if (!isValidEmail(user.email)) {
                _error.value = "Correo electrónico no válido"
                return@launch
            }

            try {
                val result = loginUseCase(user)

                result.onSuccess { data ->
                    _success.value = true
                    _error.value = ""
                    _token.value = data.token
                    _idUser.value = data.user.id_user.toString()
                    loginResponse.value = data
                }.onFailure { exception ->
                    _error.value = "Usuario o Contraseña incorrecta"
                }
            } catch (exception: Exception) {
                _error.value = "Error al autenticar"
            }
        }
        return loginResponse
    }


}
