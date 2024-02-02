package com.ecommerce.task.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: Int, val image: String
) : Parcelable