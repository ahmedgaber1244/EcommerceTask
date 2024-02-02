package com.ecommerce.task.ui.productDetails


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
import com.ecommerce.task.data.model.Product
import com.ecommerce.task.data.model.Variation
import com.ecommerce.task.databinding.ProductDetailsBinding
import com.ecommerce.task.ui.base.BaseActivity
import com.ecommerce.task.util.apiStatus.RequestStatus
import com.ecommerce.task.util.snackbar.customSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetails : Fragment(), ProductsClickEvents {
    private lateinit var binding: ProductDetailsBinding
    private lateinit var viewModel: ProductDetailsViewModel
    private val productsAdapter by lazy {
        ProductsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ProductDetailsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]
        val productId = ProductDetailsArgs.fromBundle(requireArguments()).productId
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.productId = productId
        binding.cart = Cart(productId = productId, qty = 1)
        viewModel.setProductId(productId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView2.adapter = productsAdapter
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.addQty.setOnClickListener {
            val inCart = binding.inCart ?: false
            binding.cart?.let {
                if (!inCart) {
                    it.qty += 1
                    it.product?.price?.let { price ->
                        it.total = (price.toDoubleOrNull() ?: 0.0) * it.qty
                    }
                    binding.cart = it
                } else {
                    viewModel.addQty(it)
                }
            }
        }
        binding.minusQty.setOnClickListener {
            val inCart = binding.inCart ?: false
            binding.cart?.let {
                if (!inCart) {
                    it.qty -= 1
                    it.product?.price?.let { price ->
                        it.total = (price.toDoubleOrNull() ?: 0.0) * it.qty
                    }
                    binding.cart = it
                } else {
                    viewModel.minusQty(it)
                }
            }
        }
        binding.variationsVal.setOnItemClickListener { parent, _, position, _ ->
            val selectedVariation: Variation = parent.adapter.getItem(position) as Variation
            binding.cart?.let {
                it.variationId = selectedVariation.id
                it.variationName = selectedVariation.title
                binding.cart = it
            }
        }
        binding.fav.setOnClickListener {
            binding.productId?.let {
                viewModel.favToggle(it)
            }
        }
        binding.seeMore.setOnClickListener {
            binding.data?.let {
                binding.textView11.text = it.description
                binding.seeMore.visibility = View.GONE
            }
        }
        viewModel.isFav.observe(viewLifecycleOwner) {
            binding.isFav = it
        }
        viewModel.cart.observe(viewLifecycleOwner) {
            binding.inCart = it != null
            it?.let {
                binding.cart = it
            }
        }
        viewModel.cartsList.observe(viewLifecycleOwner) {
            productsAdapter.setCartItems(it)
        }
        lifecycleScope.launch {
            viewModel.getItemsByIdRes.collect {
                when (it) {
                    is RequestStatus.Idle -> {
                        BaseActivity.progress.hide()
                    }

                    is RequestStatus.Loading -> {
                        BaseActivity.progress.show()
                    }

                    is RequestStatus.Success -> {
                        BaseActivity.progress.hide()
                        it.data.results?.let { product ->
                            viewModel.getRelatedProducts(product.brand_id)
                            binding.data = product
                            binding.cart?.let { cart ->
                                cart.product = product
                                cart.total = product.price.toDoubleOrNull() ?: 0.0
                                binding.cart = cart
                            }
                            val mViewPagerAdapter = ImagesViewPagerAdapter(
                                binding.viewpager.context, product.images
                            )
                            binding.viewpager.adapter = mViewPagerAdapter
                            binding.viewpager.startAutoScroll()
                            binding.viewpager.interval = 3000
                            binding.viewpager.isCycle = true
                            binding.viewpager.setStopScrollWhenTouch(true)
                            binding.dotsIndicator.setViewPager(binding.viewpager)
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
        viewModel.relatedProductsRes.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                productsAdapter.submitData(it)
            }
        }
    }

    override fun select(data: Product) {
        findNavController().navigate(ProductDetailsDirections.actionProductDetailsSelf(data.id))
    }

    override fun fav(data: Product) {
        viewModel.favToggle(data.id)
    }

    override fun addToCart(data: Product) {
        val cart = Cart(
            productId = data.id, product = data, qty = 1, total = data.price.toDoubleOrNull() ?: 0.0
        )
        viewModel.addCart(cart)
    }
}
