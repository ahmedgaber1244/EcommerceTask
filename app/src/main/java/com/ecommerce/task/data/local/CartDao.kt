package com.ecommerce.task.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.data.model.Fav

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(cart: Cart)

    @Query("select * from Cart")
    fun getCarts(): LiveData<List<Cart>>

    @Query("select * from Cart where productId=:productId")
    fun getCartByProductId(productId: Int): LiveData<Cart?>

    @Query("update Cart set qty=:qty , total=:total where productId=:cartId")
    fun updateCartQty(cartId: Int, qty: Int, total: Double)

    @Query("delete from Cart where productId=:cartId")
    fun deleteCart(cartId: Int)
    @Query("delete from Cart")
    fun clearCart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(fav: Fav)

    @Query("delete from Fav where productId=:productId")
    fun deleteFav(productId: Int)

    @Query("select exists (select * from Fav where productId=:productId)")
    fun checkLiveFavExists(productId: Int): LiveData<Boolean>

    @Query("select exists (select * from Fav where productId=:productId)")
    fun checkFavExists(productId: Int): Boolean
}