package com.ecommerce.task.util.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.ecommerce.task.AppSession.Companion.currentLang
import com.ecommerce.task.data.model.ErrorRes
import com.ecommerce.task.util.helper.Constants.debounceTimer
import com.squareup.moshi.Moshi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.debounce
import okhttp3.ResponseBody


@OptIn(FlowPreview::class)
fun EditText.textChangesFlow(): Flow<String> {
    return channelFlow {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.let { this@channelFlow.trySend(it).isSuccess }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        addTextChangedListener(textWatcher)

        awaitClose { removeTextChangedListener(textWatcher) }
    }.debounce(debounceTimer)
}

fun convertErrorBody(errorBody: ResponseBody?): ErrorRes? {
    return try {
        errorBody?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorRes::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}

fun isCurrentLangAr(): Boolean {
    return currentLang.contentEquals("ar", true)
}


