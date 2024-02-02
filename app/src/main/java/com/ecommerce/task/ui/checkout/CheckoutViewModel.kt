package com.ecommerce.task.ui.checkout

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerce.task.R
import com.ecommerce.task.data.RepoOperations
import com.ecommerce.task.data.bodyReq.CreateOrdersReq
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.data.model.CreateOrdersRes
import com.ecommerce.task.data.model.ErrorRes
import com.ecommerce.task.util.apiStatus.RequestStatus
import com.ecommerce.task.util.enums.PaymentMethods
import com.ecommerce.task.util.helper.convertErrorBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val repo: RepoOperations) : ViewModel() {
    private val _paymentMethodsRes: MutableStateFlow<List<PaymentMethods>> =
        MutableStateFlow(PaymentMethods.entries)
    val paymentMethodsRes: StateFlow<List<PaymentMethods>> = _paymentMethodsRes
    private val _createOrdersRes: MutableStateFlow<RequestStatus<CreateOrdersRes>> =
        MutableStateFlow(RequestStatus.Idle())
    val createOrdersRes: StateFlow<RequestStatus<CreateOrdersRes>> = _createOrdersRes
    private val _errorRes: MutableStateFlow<RequestStatus<ErrorRes>> =
        MutableStateFlow(RequestStatus.Idle())
    val errorRes: StateFlow<RequestStatus<ErrorRes>> = _errorRes.asStateFlow()
    private val _validate: MutableStateFlow<Int> = MutableStateFlow(0)
    val validate: StateFlow<Int> = _validate
    val cartsList: LiveData<List<Cart>>
        get() = repo.getCarts()

    fun createOrder(req: CreateOrdersReq) {
        if (req.name.isNotEmpty() && req.phone.isNotEmpty() && Patterns.PHONE.matcher(req.phone)
                .matches()
        ) {
            viewModelScope.launch {
                onCreateOrder(req)
            }
        } else if (req.phone.isEmpty() || !Patterns.PHONE.matcher(req.phone).matches()) {
            _validate.value = R.string.pleaseAddPhoneNumber
        } else {
            _validate.value = R.string.pleaseAddYourName
        }
    }

    private suspend fun onCreateOrder(req: CreateOrdersReq) {
        try {
            _createOrdersRes.value = RequestStatus.Loading()
            val data = repo.createOrders(req)
            if (data.isSuccessful) {
                data.body()?.let {
                    _createOrdersRes.value = RequestStatus.Success(it)
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
            _createOrdersRes.value = RequestStatus.NetworkLost()
        }
    }

    fun resetValidation() {
        _validate.value = 0
    }

    fun clearCart() {
        repo.clearCart()
    }
}