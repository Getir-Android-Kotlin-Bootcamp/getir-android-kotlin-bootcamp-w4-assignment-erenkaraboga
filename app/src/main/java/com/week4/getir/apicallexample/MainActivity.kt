package com.week4.getir.apicallexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userSetUpObservers()

    }
    private fun userSetUpObservers() {
        viewModel.getUserState()
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleUserState(state) }
            .launchIn(lifecycleScope)
    }
    private fun handleUserState(state: MainViewModel.UserViewState) {
        when (state) {
            MainViewModel.UserViewState.Init -> Unit
            is MainViewModel.UserViewState.Error -> this.let {
                Toast.makeText(this, "Error when fetch user", Toast.LENGTH_SHORT).show()
            }
            is MainViewModel.UserViewState.Success -> state.data?.let {
                onUserFetchedSuccess(it)
            }
            is MainViewModel.UserViewState.IsLoading -> handleLoading(state.isLoading)
        }
    }
    private fun onUserFetchedSuccess(data: String) {
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show()

    }
    private fun handleLoading(loading: Boolean) {}

}