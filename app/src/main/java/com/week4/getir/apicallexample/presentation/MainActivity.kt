package com.week4.getir.apicallexample.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.week4.getir.apicallexample.R
import com.week4.getir.apicallexample.data.model.response.ProfileResponseModel
import com.week4.getir.apicallexample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userSetUpObservers()

    }

    private fun userSetUpObservers() {
        viewModel.getUserState()
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleUserState(state) }
            .launchIn(lifecycleScope)

        viewModel.getProfileState()
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleProfileState(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleUserState(state: MainViewModel.UserViewState) {
        when (state) {
            MainViewModel.UserViewState.Init -> Unit
            is MainViewModel.UserViewState.Error -> this.let {
                Toast.makeText(this, getString(R.string.error_when_fetch_user), Toast.LENGTH_SHORT).show()
            }

            is MainViewModel.UserViewState.Success -> state.data?.let {
                onUserFetchedSuccess(it)
            }

            is MainViewModel.UserViewState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleProfileState(state: MainViewModel.ProfileViewState) {
        when (state) {
            MainViewModel.ProfileViewState.Init -> Unit
            is MainViewModel.ProfileViewState.Error -> this.let {
                Toast.makeText(this,
                    getString(R.string.error_when_fetch_profile), Toast.LENGTH_SHORT).show()
            }

            is MainViewModel.ProfileViewState.Success -> state.data?.let {
                onProfileFetchSuccess(it)
            }

            is MainViewModel.ProfileViewState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun onUserFetchedSuccess(data: String) {
        viewModel.getProfile(data)
        binding.userId.text = data
    }

    private fun onProfileFetchSuccess(data: ProfileResponseModel) {
        val userInfo =
            getString(R.string.email_fullname_password, data.email, data.fullName, data.password)
        binding.userInfo.text = userInfo
    }

    private fun handleLoading(loading: Boolean) {
        binding.progress.isVisible = loading
    }

}