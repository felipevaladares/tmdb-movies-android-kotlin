package com.arctouch.codechallenge.base

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job

abstract class BaseViewModel: ViewModel() {

    val viewModelJob = Job()
    var viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}