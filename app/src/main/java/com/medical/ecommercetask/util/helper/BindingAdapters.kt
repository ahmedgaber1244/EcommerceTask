package com.medical.ecommercetask.util.helper

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("setUnderLine")
fun bindUnderLine(textView: TextView, data: Boolean) {
    textView.setPaintFlags(textView.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
}

@BindingAdapter("setAttendTime")
fun setAttendTime(textView: TextView, data: String?) {
    val txt = "---"
    textView.text = data?.ifEmpty { txt } ?: txt
}
