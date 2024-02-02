package com.ecommerce.task.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.task.data.model.GetHomeData
import com.ecommerce.task.databinding.ProductItemBinding
import com.ecommerce.task.databinding.ProductItemBinding.inflate

class ProductsAdapter(private val clickListener: ProductsClickEvents) :
    ListAdapter<GetHomeData, ProductsAdapter.ViewHolder>(
        ProductsDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    class ViewHolder private constructor(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ProductsClickEvents, item: GetHomeData) {
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

class ProductsDiffCallback : DiffUtil.ItemCallback<GetHomeData>() {
    override fun areItemsTheSame(oldItem: GetHomeData, newItem: GetHomeData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GetHomeData, newItem: GetHomeData
    ): Boolean {
        return oldItem == newItem
    }
}

interface ProductsClickEvents {
    fun select(data: GetHomeData)
    fun fav(data: GetHomeData)
    fun addToCart(data: GetHomeData)
}
