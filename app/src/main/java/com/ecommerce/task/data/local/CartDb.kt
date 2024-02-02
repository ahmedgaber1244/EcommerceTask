package com.ecommerce.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.data.model.Fav

@Database(entities = [Cart::class, Fav::class], version = 1)
@TypeConverters(DbConverter::class)
abstract class CartDb : RoomDatabase() {
    abstract fun dbOperations(): CartDao
}