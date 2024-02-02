package com.ecommerce.task.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.task.data.model.GetHomeData
import com.ecommerce.task.databinding.CategoryItemBinding
import com.ecommerce.task.databinding.CategoryItemBinding.inflate

class CategoryAdapter : ListAdapter<GetHomeData, CategoryAdapter.ViewHolder>(
    CategoryDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetHomeData) {
            binding.data = item
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

class CategoryDiffCallback : DiffUtil.ItemCallback<GetHomeData>() {
    override fun areItemsTheSame(oldItem: GetHomeData, newItem: GetHomeData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GetHomeData, newItem: GetHomeData
    ): Boolean {
        return oldItem == newItem
    }
}

interface CategoryClickEvents {
    fun select(data: GetHomeData)
    fun fav(data: GetHomeData)
    fun addToCart(data: GetHomeData)
}
