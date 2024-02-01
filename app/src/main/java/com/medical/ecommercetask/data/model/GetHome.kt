package com.medical.ecommercetask.data.model

data class GetHome(
    val data: List<GetHomeData>,
    val sort: Int,
    val title: String,
    val type: Int
)