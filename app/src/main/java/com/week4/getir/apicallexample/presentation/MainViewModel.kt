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
                    _userState.value = UserViewState.IsLoading(false)

                }
                is Resource.Loading -> {
                    _userState.value = UserViewState.IsLoading(true)

                }
                is Resource.Success -> {
                    _userState.value = UserViewState.Success(it.data)
                    _userState.value = UserViewState.IsLoading(false)
                }
            }

        }.launchIn(viewModelScope)
    }
     fun getProfile(userId : String) {
        profileUseCase.invoke(userId).onEach {
            when (it) {
                is Resource.Error -> {
                    _profileState.value = ProfileViewState.Error(it.message)
                    _profileState.value = ProfileViewState.IsLoading(false)

                }
                is Resource.Loading -> {
                    _profileState.value = ProfileViewState.IsLoading(true)
                }
                is Resource.Success -> {
                    _profileState.value = ProfileViewState.Success(it.data)
                    _profileState.value = ProfileViewState.IsLoading(false)

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
