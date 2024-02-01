package com.medical.ecommercetask.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medical.ecommercetask.util.dataStore.DataStoreUtil
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
    private val _state = MutableStateFlow(false)
    val state: StateFlow<Boolean> = _state.asStateFlow()
    private val _locality = MutableStateFlow("")
    val locality: StateFlow<String> = _locality.asStateFlow()
    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token.asStateFlow()
    private val _userType = MutableStateFlow("")
    val userType: StateFlow<String> = _userType.asStateFlow()
    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()
    private val _userId = MutableStateFlow(0)
    val userId: StateFlow<Int> = _userId.asStateFlow()
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()
    private val _userPhone = MutableStateFlow("")
    val userPhone: StateFlow<String> = _userPhone.asStateFlow()
    private val _nightMode = MutableStateFlow(0)
    val nightMode: StateFlow<Int> = _nightMode.asStateFlow()

    init {
        getLocality()
        getNightMode()
        getState()
        getToken()
        getUserId()
        getUserType()
        getUserName()
        getUserPhone()
        getUserEmail()
    }

    private fun getLocality() {
        viewModelScope.launch {
            dataStoreUtil.localityFlow.collect {
                _locality.value = it
            }
        }
    }

    private fun getState() {
        viewModelScope.launch {
            dataStoreUtil.stateFlow.collect {
                _state.value = it
            }
        }
    }

    private fun getToken() {
        viewModelScope.launch {
            dataStoreUtil.tokenFlow.collect {
                _token.value = it
            }
        }
    }

    private fun getUserId() {
        viewModelScope.launch {
            dataStoreUtil.userIdFlow.collect {
                _userId.value = it
            }
        }
    }

    private fun getUserName() {
        viewModelScope.launch {
            dataStoreUtil.userNameFlow.collect {
                _userName.value = it
            }
        }
    }

    private fun getUserPhone() {
        viewModelScope.launch {
            dataStoreUtil.userMobileFlow.collect {
                _userPhone.value = it
            }
        }
    }

    private fun getUserType() {
        viewModelScope.launch {
            dataStoreUtil.userTypeFlow.collect {
                _userType.value = it
            }
        }
    }

    private fun getUserEmail() {
        viewModelScope.launch {
            dataStoreUtil.userEmailFlow.collect {
                _userEmail.value = it
            }
        }
    }

    private fun getNightMode() {
        viewModelScope.launch {
            dataStoreUtil.nightModeFlow.collect {
                _nightMode.value = it
            }
        }
    }
}