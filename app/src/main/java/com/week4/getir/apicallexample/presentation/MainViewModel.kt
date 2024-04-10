package com.week4.getir.apicallexample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.week4.getir.apicallexample.common.Resource
import com.week4.getir.apicallexample.data.model.request.LoginRequestModel
import com.week4.getir.apicallexample.data.model.response.ProfileResponseModel
import com.week4.getir.apicallexample.domain.use_cases.LoginUseCase
import com.week4.getir.apicallexample.domain.use_cases.ProfileUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val profileUseCase: ProfileUseCase


) : ViewModel() {

    private val _userState = MutableStateFlow<UserViewState>(UserViewState.Init)

    fun getUserState(): StateFlow<UserViewState> = _userState.asStateFlow()


    private val _profileState = MutableStateFlow<ProfileViewState>(ProfileViewState.Init)

    fun getProfileState(): StateFlow<ProfileViewState> = _profileState.asStateFlow()
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
     fun getProfile(userId : String) {
        profileUseCase.invoke(userId).onEach {
            when (it) {
                is Resource.Error -> {
                    _profileState.value = ProfileViewState.Error(it.message)
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    _profileState.value = ProfileViewState.Success(it.data)
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
    sealed class ProfileViewState {
        data object Init : ProfileViewState()
        data class Success(val data: ProfileResponseModel?) : ProfileViewState()
        data class IsLoading(val isLoading: Boolean) : ProfileViewState()
        data class Error(val error: String) : ProfileViewState()
    }
}
