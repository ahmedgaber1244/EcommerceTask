package com.ecommerce.task.ui.cartCheckout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ecommerce.task.data.RepoOperations
import com.ecommerce.task.data.model.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartCheckoutViewModel @Inject constructor(private val repo: RepoOperations) : ViewModel() {
    val cartsList: LiveData<List<Cart>>
        get() = repo.getCarts()
    fun addQty(data: Cart) {
        data.qty += 1
        data.product?.price?.let { price ->
            data.total = (price.toDoubleOrNull() ?: 0.0) * data.qty
        }
        repo.updateCartQty(data.productId, data.qty, data.total)
    }

    fun minusQty(data: Cart) {
        if (data.qty > 1) {
            data.qty -= 1
            data.product?.price?.let { price ->
                data.total = (price.toDoubleOrNull() ?: 0.0) * data.qty
            }
            repo.updateCartQty(data.productId, data.qty, data.total)
        } else {
            repo.deleteCart(data.productId)
        }
    }


}