package com.ecommerce.task.data

import com.ecommerce.task.AppSession.Companion.currentLang
import com.ecommerce.task.data.bodyReq.CreateOrdersReq
import com.ecommerce.task.data.bodyReq.GetItemsByIdReq
import com.ecommerce.task.data.bodyReq.GetRelatedProductsReq
import com.ecommerce.task.data.local.CartDao
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.data.model.Fav
import com.ecommerce.task.data.remote.RemoteService
import com.ecommerce.task.util.helper.Constants
import javax.inject.Inject

class RepoOperations @Inject constructor(
    private val repository: RemoteService, private val dao: CartDao
) {
    suspend fun getHome() = repository.getHome(Constants.appVersion, Constants.auth, currentLang)
    suspend fun getItemsById(req: GetItemsByIdReq) =
        repository.getItemsById(Constants.appVersion, Constants.auth, currentLang, req)

    suspend fun getRelatedProducts(req: GetRelatedProductsReq) =
        repository.getRelatedProducts(Constants.appVersion, Constants.auth, currentLang, req)

    suspend fun createOrders(req: CreateOrdersReq) =
        repository.createOrders(Constants.appVersion, Constants.auth, currentLang, req)

    fun insertCart(cart: Cart) = dao.insertCart(cart)
    fun getCarts() = dao.getCarts()
    fun getCartByProductId(productId: Int) = dao.getCartByProductId(productId)
    fun updateCartQty(cartId: Int, qty: Int, total: Double) = dao.updateCartQty(cartId, qty,total)
    fun deleteCart(cartId: Int) = dao.deleteCart(cartId)
    fun insertFav(fav: Fav) = dao.insertFav(fav)
    fun deleteFav(productId: Int) = dao.deleteFav(productId)
    fun checkLiveFavExists(productId: Int) = dao.checkLiveFavExists(productId)
    fun checkFavExists(productId: Int) = dao.checkFavExists(productId)
    fun clearCart() = dao.clearCart()
}