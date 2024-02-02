package com.ecommerce.task.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Cart(
    var product: Product?=null,
    var variationId: Int=0,
    var variationName: String="",
    @PrimaryKey
    var productId: Int=0,
    var qty: Int=0,
    var total: Double=0.0,
) : Parcelable