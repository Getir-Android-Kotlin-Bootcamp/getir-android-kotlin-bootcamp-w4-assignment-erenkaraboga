package com.week4.getir.apicallexample

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase


) : ViewModel() {

    private val _userState = MutableStateFlow<UserViewState>(UserViewState.Init)

    fun getUserState(): StateFlow<UserViewState> = _userState.asStateFlow()


    init {
        getMyUser()
    }


    private fun getMyUser() {
        loginUseCase.invoke(LoginRequestModel("eren@gmail.com","Eren12345")).onEach {
            when (it) {
                is Resource.Error -> {
                    _userState.value = UserViewState.Error(it.message)
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    _userState.value = UserViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }


    sealed class UserViewState {
        data object Init : UserViewState()
        data class Success(val data: String?) : UserViewState()
        data class IsLoading(val isLoading: Boolean) : UserViewState()
        data class Error(val error: String) : UserViewState()
    }
}
