package com.medical.ecommercetask.data

import com.medical.ecommercetask.AppSession.Companion.currentLang
import com.medical.ecommercetask.data.bodyReq.CreateOrdersReq
import com.medical.ecommercetask.data.bodyReq.GetItemsByIdReq
import com.medical.ecommercetask.data.bodyReq.GetRelatedProductsReq
import com.medical.ecommercetask.data.remote.RemoteService
import com.medical.ecommercetask.util.helper.Constants
import javax.inject.Inject

class RepoOperations @Inject constructor(
    private val repository: RemoteService
) {
    suspend fun getHome() = repository.getHome(Constants.appVersion, Constants.auth, currentLang)
    suspend fun getItemsById(req: GetItemsByIdReq) =
        repository.getItemsById(Constants.appVersion, Constants.auth, currentLang, req)

    suspend fun getRelatedProducts(req: GetRelatedProductsReq) =
        repository.getRelatedProducts(Constants.appVersion, Constants.auth, currentLang, req)

    suspend fun createOrders(req: CreateOrdersReq) =
        repository.createOrders(Constants.appVersion, Constants.auth, currentLang, req)
}