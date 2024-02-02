package com.ecommerce.task.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.task.data.model.GetHome
import com.ecommerce.task.databinding.AdsItemBinding
import com.ecommerce.task.databinding.CategoriesItemBinding
import com.ecommerce.task.databinding.OffersItemBinding
import com.ecommerce.task.databinding.TrendingItemBinding

class HomeAdapter(private val clickListener: ProductsClickEvents) :
    ListAdapter<GetHome, RecyclerView.ViewHolder>(
        GetHomeDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val type = when (viewType) {
            1 -> AdsViewHolder.from(parent)
            2 -> CategoriesViewHolder.from(parent)
            3 -> OffersViewHolder.from(parent)
            else -> ProductViewHolder.from(parent)
        }
        return type
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AdsViewHolder -> {
                holder.bind(getItem(position))
            }

            is CategoriesViewHolder -> {
                holder.bind(getItem(position))
            }

            is OffersViewHolder -> {
                holder.bind(clickListener,getItem(position))
            }

            is ProductViewHolder -> {
                holder.bind(clickListener, getItem(position))
            }
        }
    }

    class AdsViewHolder private constructor(private val binding: AdsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetHome) {
            binding.data = item
            val mViewPagerAdapter = AdsViewPagerAdapter(
                binding.viewpager.context, item.data
            )
            binding.viewpager.adapter = mViewPagerAdapter
            binding.viewpager.startAutoScroll()
            binding.viewpager.interval = 3000
            binding.viewpager.isCycle = true
            binding.viewpager.setStopScrollWhenTouch(true)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AdsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AdsItemBinding.inflate(layoutInflater, parent, false)
                return AdsViewHolder(binding)
            }
        }
    }

    class CategoriesViewHolder private constructor(private val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetHome) {
            binding.data = item
            val categoryAdapter = CategoryAdapter()
            binding.recyclerView2.adapter = categoryAdapter
            categoryAdapter.submitList(item.data)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoriesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoriesItemBinding.inflate(layoutInflater, parent, false)
                return CategoriesViewHolder(binding)
            }
        }
    }

    class OffersViewHolder private constructor(private val binding: OffersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ProductsClickEvents, item: GetHome) {
            binding.data = item
            val mViewPagerAdapter = OffersViewPagerAdapter(
                binding.viewpager.context, item.data,clickListener
            )
            binding.viewpager.adapter = mViewPagerAdapter
            binding.viewpager.startAutoScroll()
            binding.viewpager.interval = 3000
            binding.viewpager.isCycle = true
            binding.viewpager.setStopScrollWhenTouch(true)
            binding.dotsIndicator.setViewPager(binding.viewpager)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): OffersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OffersItemBinding.inflate(layoutInflater, parent, false)
                return OffersViewHolder(binding)
            }
        }
    }

    class ProductViewHolder private constructor(private val binding: TrendingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ProductsClickEvents, item: GetHome) {
            binding.data = item
            val productsAdapter = ProductsAdapter(clickListener)
            binding.recyclerView2.adapter = productsAdapter
            productsAdapter.submitList(item.data)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TrendingItemBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding)
            }
        }
    }
}

class GetHomeDiffCallback : DiffUtil.ItemCallback<GetHome>() {
    override fun areItemsTheSame(oldItem: GetHome, newItem: GetHome): Boolean {
        return oldItem.title.contentEquals(newItem.title)
    }

    override fun areContentsTheSame(
        oldItem: GetHome, newItem: GetHome
    ): Boolean {
        return oldItem == newItem
    }
}

