package com.medical.ecommercetask.data.model

data class Variation(
    val id: Int,
    val image: List<Image>,
    val price: String,
    val title: String
)