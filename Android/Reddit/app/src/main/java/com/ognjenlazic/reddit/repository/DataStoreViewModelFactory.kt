package com.ognjenlazic.reddit.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ognjenlazic.reddit.viewModel.LoginViewModel

class DataStoreViewModelFactory(private val dataStorePreferenceRepository: DataStorePreferenceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T//
        }
        throw IllegalStateException()
    }
}