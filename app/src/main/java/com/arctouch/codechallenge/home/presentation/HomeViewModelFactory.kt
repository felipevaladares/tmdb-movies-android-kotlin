package com.arctouch.codechallenge.home.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class HomeViewModelFactory: ViewModelProvider.Factory  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel() as T
    }
}