package com.ecommerce.task.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Variation(
    val id: Int,
    val image: List<Image>,
    val price: String,
    val title: String
): Parcelable