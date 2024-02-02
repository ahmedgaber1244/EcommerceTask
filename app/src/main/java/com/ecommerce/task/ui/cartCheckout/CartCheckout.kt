package com.ecommerce.task.ui.cartCheckout


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.databinding.CartCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartCheckout : Fragment(), CartClickEvents {
    private lateinit var binding: CartCheckoutBinding
    private lateinit var viewModel: CartCheckoutViewModel
    private val adapter by lazy {
        CartsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CartCheckoutBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[CartCheckoutViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.checkout.setOnClickListener {
            findNavController().navigate(CartCheckoutDirections.actionCartCheckoutToCheckout2())
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        viewModel.cartsList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            val sum = it.sumOf { cart: Cart -> cart.total }
            binding.subTotal=sum
            binding.discount=0.0
            binding.delivery=0.0
            binding.total=sum
            binding.noData = it.isEmpty()
        }
    }

    override fun add(data: Cart) {
        viewModel.addQty(data)
    }

    override fun minus(data: Cart) {
        viewModel.minusQty(data)
    }
}
