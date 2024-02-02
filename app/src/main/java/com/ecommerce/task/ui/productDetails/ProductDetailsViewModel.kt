package com.ecommerce.task.ui.productDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.ecommerce.task.data.RepoOperations
import com.ecommerce.task.data.bodyReq.GetItemsByIdReq
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.data.model.ErrorRes
import com.ecommerce.task.data.model.Fav
import com.ecommerce.task.data.model.GetItemsByIdRes
import com.ecommerce.task.util.apiStatus.RequestStatus
import com.ecommerce.task.util.helper.convertErrorBody
import com.ecommerce.task.util.pagingSource.ProductPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val repo: RepoOperations) : ViewModel() {
    private val _getItemsByIdRes: MutableStateFlow<RequestStatus<GetItemsByIdRes>> =
        MutableStateFlow(RequestStatus.Idle())
    val getItemsByIdRes: StateFlow<RequestStatus<GetItemsByIdRes>> = _getItemsByIdRes
    private val _errorRes: MutableStateFlow<RequestStatus<ErrorRes>> =
        MutableStateFlow(RequestStatus.Idle())
    val errorRes: StateFlow<RequestStatus<ErrorRes>> = _errorRes.asStateFlow()
    private val productId = MutableLiveData<Int>()
    private val brandId = MutableLiveData<Int>()
    val relatedProductsRes = brandId.switchMap {
        Pager(
            PagingConfig(pageSize = 20)
        ) {
            ProductPagingSource(it, repo)
        }.liveData.cachedIn(viewModelScope)
    }
    val isFav: LiveData<Boolean>
        get() = productId.switchMap {
            repo.checkLiveFavExists(it)
        }
    val cart: LiveData<Cart?>
        get() = productId.switchMap {
            repo.getCartByProductId(it)
        }
    val cartsList: LiveData<List<Cart>>
        get() = repo.getCarts()

    private fun getItemsById(productId: Int) {
        viewModelScope.launch {
            onGetItemsById(GetItemsByIdReq(productId))
        }
    }

    private suspend fun onGetItemsById(req: GetItemsByIdReq) {
        try {
            _getItemsByIdRes.value = RequestStatus.Loading()
            val data = repo.getItemsById(req)
            if (data.isSuccessful) {
                data.body()?.let {
                    _getItemsByIdRes.value = RequestStatus.Success(it)
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
            _getItemsByIdRes.value = RequestStatus.NetworkLost()
        }
    }

    fun getRelatedProducts(id: Int) {
        brandId.value = id
    }

    fun favToggle(productId: Int) {
        if (repo.checkFavExists(productId)) {
            repo.deleteFav(productId)
        } else {
            repo.insertFav(Fav(productId))
        }
    }

    fun setProductId(id: Int) {
        productId.value = id
        getItemsById(id)
    }

    fun addCart(cart: Cart) {
        repo.insertCart(cart)
    }

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