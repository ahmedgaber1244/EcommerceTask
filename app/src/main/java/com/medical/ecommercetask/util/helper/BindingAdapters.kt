package com.medical.ecommercetask.util.helper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.medical.ecommercetask.data.model.GetHome
import com.medical.ecommercetask.data.model.GetHomeData
import com.medical.ecommercetask.data.model.Product


@BindingAdapter("setImg")
fun bindImg(imgView: ImageView, data: String?) {
    Glide.with(imgView.context).load(data ?: "").placeholder(Constants.placeholderImg).into(imgView)
}

@BindingAdapter("setProductImg")
fun bindProductImg(imgView: ImageView, data: Product?) {
    data?.let {
        Glide.with(imgView.context).load(data.image ?: "").placeholder(Constants.placeholderImg)
            .into(imgView)
    }
}

@BindingAdapter("setSectionTitle")
fun bindSectionTitle(textView: TextView, data: GetHome?) {
    data?.let {
        textView.text = it.title
    }
}

@BindingAdapter("setCategoryTitle")
fun bindCategoryTitle(textView: TextView, data: GetHomeData?) {
    data?.title?.let {
        textView.text = it
    }
}

@BindingAdapter("setOfferTitle")
fun bindOfferTitle(textView: TextView, data: GetHomeData?) {
    data?.title?.let {
        textView.text = it
    }
}

@BindingAdapter("setOfferDesc")
fun bindOfferDesc(textView: TextView, data: GetHomeData?) {
    data?.description?.let {
        textView.text = it
    }
}

@BindingAdapter("setProductName")
fun bindProductName(textView: TextView, data: Product?) {
    data?.title?.let {
        textView.text = it
    }
}

@BindingAdapter("setProductDesc")
fun bindProductDesc(textView: TextView, data: Product?) {
    data?.description?.let {
        val txt = if (it.length > 100) {
            "${it.substring(0, 100)}..."
        } else {
            it
        }
        textView.text = txt
    }
}

@BindingAdapter("setProductCategoryName")
fun bindProductCategoryName(textView: TextView, data: Product?) {
    data?.category?.let {
        textView.text = it
    }
}

@BindingAdapter("setProductPrice")
fun setProductPrice(textView: TextView, data: Product?) {
    data?.price?.let {
        textView.text = it
    }
}