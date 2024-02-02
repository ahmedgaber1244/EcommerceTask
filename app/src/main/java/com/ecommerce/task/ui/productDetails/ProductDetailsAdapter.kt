package com.ecommerce.task.ui.productDetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.data.model.Product
import com.ecommerce.task.databinding.GridProductItemBinding
import com.ecommerce.task.databinding.GridProductItemBinding.inflate

class ProductsAdapter(private val clickListener: ProductsClickEvents) :
    PagingDataAdapter<Product, ProductsAdapter.ViewHolder>(
        ProductsDiffCallback()
    ) {
    private var cartList = listOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(clickListener, it, cartList.contains(it.id)) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCartItems(data: List<Cart>?) {
        data?.let { it ->
            this.cartList = it.map { it.productId }
        }
        notifyDataSetChanged()
    }

    class ViewHolder private constructor(private val binding: GridProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ProductsClickEvents, item: Product, selected: Boolean) {
            binding.data = item
            binding.clickEvents = clickListener
            binding.added = selected
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ProductsDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Product, newItem: Product
    ): Boolean {
        return oldItem == newItem
    }
}

interface ProductsClickEvents {
    fun select(data: Product)
    fun fav(data: Product)
    fun addToCart(data: Product)
}
