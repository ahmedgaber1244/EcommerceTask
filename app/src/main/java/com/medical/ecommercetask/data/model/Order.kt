package com.medical.ecommercetask.data.model

data class Order(
    val address: String?,
    val address_id: String,
    val count_items: Int,
    val date: String,
    val delevery_fee: Int,
    val discount: Int,
    val id: Int,
    val is_delivery: String,
    val items: List<Item>,
    val payment: String,
    val payment_status: String,
    val promocode_id: Int,
    val status: String,
    val sub_total: Int,
    val time: String,
    val title_status: String,
    val total_amount: Int
)