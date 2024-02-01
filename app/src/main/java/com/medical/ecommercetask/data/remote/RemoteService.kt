package com.medical.ecommercetask.data.remote

import com.medical.ecommercetask.data.bodyReq.CreateOrdersReq
import com.medical.ecommercetask.data.bodyReq.GetItemsByIdReq
import com.medical.ecommercetask.data.bodyReq.GetRelatedProductsReq
import com.medical.ecommercetask.data.model.CreateOrdersRes
import com.medical.ecommercetask.data.model.GetHomeRes
import com.medical.ecommercetask.data.model.GetItemsByIdRes
import com.medical.ecommercetask.data.model.GetRelatedProductsRes
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