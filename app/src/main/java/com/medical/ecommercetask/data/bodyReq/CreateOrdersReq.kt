package com.medical.ecommercetask.data.bodyReq

data class CreateOrdersReq(
    val address_id: String,
    val items: List<Item>,
    val name: String,
    val payment: String,
    val phone: String,
    val promocode_id: String,
    val re_order: String
)