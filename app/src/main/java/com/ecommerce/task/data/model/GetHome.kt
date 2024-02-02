package com.ecommerce.task.data.model

data class GetHome(
    val data: List<GetHomeData>,
    val sort: Int,
    val title: String,
    val type: Int
)