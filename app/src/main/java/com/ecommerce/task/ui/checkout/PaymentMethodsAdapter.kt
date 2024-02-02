package com.ecommerce.task.ui.checkout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.task.databinding.PaymentMethodItemBinding
import com.ecommerce.task.databinding.PaymentMethodItemBinding.inflate
import com.ecommerce.task.util.enums.PaymentMethods

class PaymentMethodsAdapter(private val clickListener: PaymentMethodClickEvents) :
    ListAdapter<PaymentMethods, PaymentMethodsAdapter.ViewHolder>(
        PaymentMethodDiffCallback()
    ) {
    private var selectedMethod = PaymentMethods.cod
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item, selectedMethod == item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelected(data: PaymentMethods) {
        this.selectedMethod = data
        notifyDataSetChanged()
    }

    class ViewHolder private constructor(private val binding: PaymentMethodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: PaymentMethodClickEvents, item: PaymentMethods, selected: Boolean) {
            binding.data = item
            binding.clickEvents = clickListener
            binding.selected = selected
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

class PaymentMethodDiffCallback : DiffUtil.ItemCallback<PaymentMethods>() {
    override fun areItemsTheSame(oldItem: PaymentMethods, newItem: PaymentMethods): Boolean {
        return oldItem.ordinal == newItem.ordinal
    }

    override fun areContentsTheSame(
        oldItem: PaymentMethods, newItem: PaymentMethods
    ): Boolean {
        return oldItem == newItem
    }
}

interface PaymentMethodClickEvents {
    fun select(data: PaymentMethods)
}
