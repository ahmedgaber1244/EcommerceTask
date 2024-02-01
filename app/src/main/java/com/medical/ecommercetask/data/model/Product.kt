package com.medical.ecommercetask.data.model

data class Product(
    val brand_id: Int,
    val category: String,
    val category_id: Int,
    val description: String,
    val id: Int,
    val image: String,
    val images: List<Image>,
    val is_favorite: Boolean,
    val price: String,
    val sold_out: Boolean,
    val status: String,
    val subcategory_id: Int,
    val title: String,
    val variations: List<Variation>
)