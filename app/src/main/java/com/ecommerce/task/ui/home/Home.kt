package com.ecommerce.task.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ecommerce.task.R
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.data.model.GetHome
import com.ecommerce.task.data.model.GetHomeData
import com.ecommerce.task.data.model.Product
import com.ecommerce.task.databinding.HomeBinding
import com.ecommerce.task.ui.base.BaseActivity
import com.ecommerce.task.util.apiStatus.RequestStatus
import com.ecommerce.task.util.snackbar.customSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : Fragment(), ProductsClickEvents {
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(this)
    }
    private lateinit var binding: HomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = HomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView2.adapter = homeAdapter
        binding.cart.setOnClickListener {
            findNavController().navigate(HomeDirections.actionHome2ToCartCheckout())
        }
        lifecycleScope.launch {
            viewModel.getHomeRes.collect {
                when (it) {
                    is RequestStatus.Idle -> {
                        BaseActivity.progress.hide()
                    }

                    is RequestStatus.Loading -> {
                        BaseActivity.progress.show()
                    }

                    is RequestStatus.Success -> {
                        BaseActivity.progress.hide()
                        it.data.results?.let { res ->
                            val sortedBy = res.sortedBy { getHome: GetHome -> getHome.sort }
                            homeAdapter.submitList(sortedBy)
                        }
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
        viewModel.cartsList.observe(viewLifecycleOwner) {
            binding.cartTotalItems = it.size
        }
    }

    override fun select(data: GetHomeData) {
        findNavController().navigate(HomeDirections.actionHome2ToProductDetails(data.id))
    }

    override fun fav(data: GetHomeData) {
        viewModel.favToggle(data.id)
    }

    override fun addToCart(data: GetHomeData) {
        val cart = Cart(
            productId = data.id, product = Product(
                0,
                "",
                data.category_id ?: 0,
                data.description ?: "",
                data.id,
                data.image,
                listOf(),
                false,
                data.price,
                false,
                "",
                0,
                data.title ?: "",
                listOf()
            ), qty = 1, total = data.price.toDoubleOrNull() ?: 0.0
        )
        viewModel.addCart(cart)
    }
}
