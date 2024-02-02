package com.ecommerce.task.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Fav(
    @PrimaryKey var productId: Int
) : Parcelable