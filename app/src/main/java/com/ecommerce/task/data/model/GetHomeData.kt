package com.ecommerce.task.data.model

data class GetHomeData(
    val brand_id: Int?,
    val category_id: Int?,
    val category_image: List<CategoryImage>?,
    val description: String?,
    val id: Int,
    val price: String,
    val image: String,
    val items_id: Int?,
    val subcategory: List<Subcategory>?,
    val title: String?
)