package com.example.state.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class RegisterViewModel() : ViewModel() {
    //private var _username = MutableLiveData<String>()
    private var _username = MutableLiveData<String>()
    val username : LiveData<String> = _username

    private var _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    fun onChangeUsername(username : String) {
        _username.value = username
    }

    fun onChangePassword (password : String) {
        _password.value = password
    }
}