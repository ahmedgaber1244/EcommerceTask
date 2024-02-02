package com.ecommerce.task.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.ecommerce.task.data.model.GetHomeData
import com.ecommerce.task.databinding.OfferItemBinding

class OffersViewPagerAdapter(
    private val mContext: Context,
    private val sliderImageId: List<GetHomeData>,
    private val clickListener: ProductsClickEvents
) : PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val data: GetHomeData = sliderImageId[position]
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = OfferItemBinding.inflate(layoutInflater, container, false)
        binding.data = data
        binding.clickEvents = clickListener
        container.addView(binding.root, 0)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return sliderImageId.size
    }
}