package com.ecommerce.task.ui.cartCheckout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.databinding.CartItemBinding
import com.ecommerce.task.databinding.CartItemBinding.inflate

class CartsAdapter(private val clickListener: CartClickEvents) :
    ListAdapter<Cart, CartsAdapter.ViewHolder>(
        CartDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    class ViewHolder private constructor(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: CartClickEvents, item: Cart) {
            binding.data = item
            binding.clickEvents = clickListener
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

class CartDiffCallback : DiffUtil.ItemCallback<Cart>() {
    override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(
        oldItem: Cart, newItem: Cart
    ): Boolean {
        return oldItem == newItem
    }
}

interface CartClickEvents {
    fun add(data: Cart)
    fun minus(data: Cart)
}
