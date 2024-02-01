package com.medical.ecommercetask.data.model

data class GetRelatedProductsRes(
    val results: List<Product>,
    val status: Status
)