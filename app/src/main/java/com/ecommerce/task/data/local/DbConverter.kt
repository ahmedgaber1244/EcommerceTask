package com.ecommerce.task.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ecommerce.task.data.model.Product

class DbConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromProduct(product: Product?): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun toProduct(productJson: String): Product {
        val type = object : TypeToken<Product>() {}.type
        return gson.fromJson(productJson, type)
    }
}