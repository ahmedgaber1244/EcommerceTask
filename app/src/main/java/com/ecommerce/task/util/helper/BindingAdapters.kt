package com.ecommerce.task.util.helper

import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ecommerce.task.R
import com.ecommerce.task.data.model.Cart
import com.ecommerce.task.data.model.GetHome
import com.ecommerce.task.data.model.GetHomeData
import com.ecommerce.task.data.model.Product
import com.ecommerce.task.data.model.Variation
import com.ecommerce.task.ui.productDetails.VariationAdapter


@BindingAdapter("setImg")
fun bindImg(imgView: ImageView, data: String?) {
    Glide.with(imgView.context).load(data ?: "").placeholder(Constants.placeholderImg).into(imgView)
}

@BindingAdapter("setImg")
fun bindImg(imgView: ImageView, data: Int) {
    Glide.with(imgView.context).load(data).placeholder(Constants.placeholderImg).into(imgView)
}

@BindingAdapter("setProductImg")
fun bindProductImg(imgView: ImageView, data: Product?) {
    data?.let {
        Glide.with(imgView.context).load(data.image ?: "").placeholder(Constants.placeholderImg)
            .into(imgView)
    }
}

@BindingAdapter("setProductImg")
fun bindProductImg(imgView: ImageView, data: GetHomeData?) {
    data?.let {
        Glide.with(imgView.context).load(data.image).placeholder(Constants.placeholderImg)
            .into(imgView)
    }
}

@BindingAdapter("setSectionTitle")
fun bindSectionTitle(textView: TextView, data: GetHome?) {
    data?.let {
        textView.text = it.title
    }
}

@BindingAdapter("setVariationTitle")
fun bindVariationTitle(textView: TextView, data: Variation?) {
    data?.title?.let {
        textView.text = it
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

@BindingAdapter("setProductName")
fun bindProductName(textView: TextView, data: GetHomeData?) {
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

@BindingAdapter("setSeeMoreVisibility")
fun bindSeeMoreVisibility(textView: TextView, data: Product?) {
    data?.description?.let {
        val visibility = if (it.length > 100) {
            View.VISIBLE
        } else {
            View.GONE
        }
        textView.visibility = visibility
    }
}

@BindingAdapter("setProductDesc")
fun bindProductDesc(textView: TextView, data: GetHomeData?) {
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
        val txt = "$it " + textView.context.resources.getString(R.string.jd)
        textView.text = txt
    }
}

@BindingAdapter("setProductPrice")
fun setProductPrice(textView: TextView, data: GetHomeData?) {
    data?.price?.let {
        textView.text = it
    }
}

@BindingAdapter("setCartPrice")
fun bindCartPrice(textView: TextView, data: Cart?) {
    data?.total?.let {
        val txt = "$it " + textView.context.resources.getString(R.string.jd)
        textView.text = txt
    }
}

@BindingAdapter("setPrice")
fun bindPrice(textView: TextView, data: Double) {
    val txt = "$data " + textView.context.resources.getString(R.string.jd)
    textView.text = txt
}

@BindingAdapter("setCartQty")
fun bindCartQty(textView: TextView, data: Cart?) {
    data?.qty?.let {
        val txt = "$it " + textView.context.resources.getString(R.string.qty)
        textView.text = txt
    }
}

@BindingAdapter("setQty")
fun bindQty(textView: TextView, data: Cart?) {
    data?.qty?.let {
        val txt = "$it "
        textView.text = txt
    }
}

@BindingAdapter("setCartVariationName")
fun bindCartVariationName(textView: TextView, data: Cart?) {
    data?.variationName?.let {
        textView.text = it
    }
}

@BindingAdapter("setCartMinusIcon")
fun bindCartMinusIcon(imageButton: ImageButton, data: Cart?) {
    data?.qty?.let {
        val img = if (it == 1) R.drawable.delete else R.drawable.rectangle_228
        imageButton.setImageResource(img)
    }
}

@BindingAdapter("setVariationsAdapter")
fun bindVariationsAdapter(autoCompleteTextView: AutoCompleteTextView, data: List<Variation>?) {
    if (data != null) {
        val adapter = VariationAdapter(
            autoCompleteTextView.context, R.layout.variation_item, ArrayList<Variation>(data)
        )
        autoCompleteTextView.setAdapter(adapter)
    }
}