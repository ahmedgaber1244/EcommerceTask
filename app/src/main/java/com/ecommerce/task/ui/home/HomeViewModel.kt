package com.ecommerce.task.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerce.task.data.RepoOperations
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.data.model.ErrorRes
import com.ecommerce.task.data.model.Fav
import com.ecommerce.task.data.model.GetHomeRes
import com.ecommerce.task.util.apiStatus.RequestStatus
import com.ecommerce.task.util.helper.convertErrorBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RepoOperations) : ViewModel() {
    private val _getHomeRes: MutableStateFlow<RequestStatus<GetHomeRes>> =
        MutableStateFlow(RequestStatus.Idle())
    val getHomeRes: StateFlow<RequestStatus<GetHomeRes>> = _getHomeRes
    private val _errorRes: MutableStateFlow<RequestStatus<ErrorRes>> =
        MutableStateFlow(RequestStatus.Idle())
    val errorRes: StateFlow<RequestStatus<ErrorRes>> = _errorRes.asStateFlow()
    val cartsList: LiveData<List<Cart>>
        get() = repo.getCarts()

    init {
        getHome()
    }

    private fun getHome() {
        viewModelScope.launch {
            onGetHome()
        }
    }

    private suspend fun onGetHome() {
        try {
            _getHomeRes.value = RequestStatus.Loading()
            val data = repo.getHome()
            if (data.isSuccessful) {
                data.body()?.let {
                    _getHomeRes.value = RequestStatus.Success(it)
                }
            } else {
                val errorResponse: ErrorRes? = convertErrorBody(data.errorBody())
                if (errorResponse != null) {
                    _errorRes.value = RequestStatus.Failure(errorResponse)
                } else {
                    _errorRes.value = RequestStatus.NetworkLost()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _getHomeRes.value = RequestStatus.NetworkLost()
        }
    }

    fun favToggle(productId: Int) {
        if (repo.checkFavExists(productId)) {
            repo.deleteFav(productId)
        } else {
            repo.insertFav(Fav(productId))
        }
    }

    fun addCart(cart: Cart) {
        repo.insertCart(cart)
    }
}