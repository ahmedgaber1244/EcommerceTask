package com.ecommerce.task.data.bodyReq

data class CreateOrdersReq(
    var address_id: String="",
    var items: MutableList<Item> = mutableListOf(),
    var name: String="",
    var payment: String="",
    var phone: String="",
    var promocode_id: String="",
    var re_order: String="0"
)