package com.medical.ecommercetask.util.dataStore

import android.content.Context
import com.medical.ecommercetask.AppSession.Companion.currentLang

class SharedPref {
    fun getLang(context: Context): String {
        val sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return sharedPref?.getString("language", "ar")!!
    }

    fun setLang(context: Context, Lang: String) {
        val sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()
        sharedPref?.putString("language", Lang)
        sharedPref?.apply()
        currentLang = Lang
    }
}