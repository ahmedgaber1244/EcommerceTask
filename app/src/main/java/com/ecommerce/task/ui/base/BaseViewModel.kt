package com.ecommerce.task.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerce.task.util.dataStore.DataStoreUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val dataStoreUtil: DataStoreUtil
) : ViewModel() {
    private val _locality = MutableStateFlow("en")
    val locality: StateFlow<String> = _locality.asStateFlow()

    init {
        getLocality()
    }

    private fun getLocality() {
        viewModelScope.launch {
            dataStoreUtil.localityFlow.collect {
                _locality.value = it
            }
        }
    }
}