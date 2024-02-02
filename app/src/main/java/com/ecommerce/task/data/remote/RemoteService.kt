package com.ecommerce.task.data.remote

import com.ecommerce.task.data.bodyReq.CreateOrdersReq
import com.ecommerce.task.data.bodyReq.GetItemsByIdReq
import com.ecommerce.task.data.bodyReq.GetRelatedProductsReq
import com.ecommerce.task.data.model.CreateOrdersRes
import com.ecommerce.task.data.model.GetHomeRes
import com.ecommerce.task.data.model.GetItemsByIdRes
import com.ecommerce.task.data.model.GetRelatedProductsRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RemoteService {
    @GET("get_home")
    suspend fun getHome(
        @Header("appVersion") appVersion: String,
        @Header("Authorization") authorization: String,
        @Header("Lang") lang: String,
    ): Response<GetHomeRes>

    @POST("get_items_by_id")
    suspend fun getItemsById(
        @Header("appVersion") appVersion: String,
        @Header("Authorization") authorization: String,
        @Header("Lang") lang: String,
        @Body req: GetItemsByIdReq,
    ): Response<GetItemsByIdRes>

    @POST("get_related_products")
    suspend fun getRelatedProducts(
        @Header("appVersion") appVersion: String,
        @Header("Authorization") authorization: String,
        @Header("Lang") lang: String,
        @Body req: GetRelatedProductsReq,
    ): Response<GetRelatedProductsRes>

    @POST("create_orders")
    suspend fun createOrders(
        @Header("appVersion") appVersion: String,
        @Header("Authorization") authorization: String,
        @Header("Lang") lang: String,
        @Body req: CreateOrdersReq,
    ): Response<CreateOrdersRes>
}