package com.ecommerce.task.ui.checkout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ecommerce.task.R
import com.ecommerce.task.data.bodyReq.CreateOrdersReq
import com.ecommerce.task.data.bodyReq.Item
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.databinding.OrderCheckoutBinding
import com.ecommerce.task.ui.base.BaseActivity
import com.ecommerce.task.util.apiStatus.RequestStatus
import com.ecommerce.task.util.enums.PaymentMethods
import com.ecommerce.task.util.snackbar.customSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Checkout : Fragment(), PaymentMethodClickEvents {
    private lateinit var binding: OrderCheckoutBinding
    private lateinit var viewModel: CheckoutViewModel
    private val paymentMethodsAdapter by lazy {
        PaymentMethodsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = OrderCheckoutBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.req = CreateOrdersReq()
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView2.adapter = paymentMethodsAdapter
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        lifecycleScope.launch {
            viewModel.paymentMethodsRes.collect {
                paymentMethodsAdapter.submitList(it)
            }
        }
        lifecycleScope.launch {
            viewModel.createOrdersRes.collect {
                when (it) {
                    is RequestStatus.Idle -> {
                        BaseActivity.progress.hide()
                    }

                    is RequestStatus.Loading -> {
                        BaseActivity.progress.show()
                    }

                    is RequestStatus.Success -> {
                        BaseActivity.progress.hide()
                        viewModel.clearCart()
                        it.data.status.massage?.let { msg ->
                            customSnackBar(requireContext(), binding.root, msg, true)
                        }
                        findNavController().navigateUp()
                    }

                    is RequestStatus.Failure -> {
                        BaseActivity.progress.hide()
                        it.data.status.massage?.let { msg ->
                            customSnackBar(requireContext(), binding.root, msg, false)
                        }
                    }

                    is RequestStatus.NetworkLost -> {
                        BaseActivity.progress.networkLost()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.errorRes.collect {
                when (it) {
                    is RequestStatus.Idle -> {
                        BaseActivity.progress.hide()
                    }

                    is RequestStatus.Loading -> {
                        BaseActivity.progress.show()
                    }

                    is RequestStatus.Success -> {
                        BaseActivity.progress.hide()
                    }

                    is RequestStatus.Failure -> {
                        BaseActivity.progress.hide()
                        customSnackBar(
                            requireContext(), binding.root, requireContext().resources.getString(
                                R.string.somethingWentWrong
                            ), false
                        )
                    }

                    is RequestStatus.NetworkLost -> {
                        BaseActivity.progress.networkLost()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.validate.collect {
                if (it>0){
                    customSnackBar(
                        requireContext(), binding.root, requireContext().resources.getString(
                            it
                        ), false
                    )
                }
            }
        }
        viewModel.cartsList.observe(viewLifecycleOwner) {
            val items = it.map { cart: Cart ->
                Item(
                    cart.productId.toString(), cart.variationId.toString(), cart.qty.toString()
                )
            }.toMutableList()
            val sum = it.sumOf { cart: Cart -> cart.total }
            binding.subTotal = sum
            binding.discount = 0.0
            binding.delivery = 0.0
            binding.total = sum
            binding.req?.let { req ->
                req.items = items
                binding.req = req
            }
        }
    }

    override fun select(data: PaymentMethods) {
        paymentMethodsAdapter.setSelected(data)
        binding.req?.let {
            it.payment = data.numberValue.toString()
            binding.req = it
        }
    }
}
