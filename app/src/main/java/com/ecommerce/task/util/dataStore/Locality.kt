package com.ecommerce.task.util.dataStore

import android.content.Context
import java.util.*

class Locality {

     fun setLocale(context: Context, Lang: String) {
        SharedPref().setLang(context,Lang)
    }

    fun getLocale(context: Context): Locale {
        val lang = SharedPref().getLang(context)
        return Locale(lang)
    }

}