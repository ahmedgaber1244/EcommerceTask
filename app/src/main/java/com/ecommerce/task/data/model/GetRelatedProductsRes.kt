package com.ecommerce.task.data.model

data class GetRelatedProductsRes(
    val results: List<Product>?,
    val status: Status
)