package com.ecommerce.task.di.localDbMd

import android.content.Context
import androidx.room.Room
import com.ecommerce.task.data.local.CartDao
import com.ecommerce.task.data.local.CartDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDbModule {
    @Provides
    @Singleton
    fun dbInst(@ApplicationContext context: Context?): CartDb {
        return Room.databaseBuilder(context!!, CartDb::class.java, "CartDB")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun dbOperationsInst(db: CartDb): CartDao {
        return db.dbOperations()
    }
}