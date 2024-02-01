package com.medical.ecommercetask.util.helper

import com.medical.ecommercetask.BuildConfig
import com.medical.ecommercetask.R

object Constants {
    val placeholderImg = R.drawable.place_holder
    const val appVersion = "android-".plus(BuildConfig.VERSION_CODE)
    const val BASE_URL = "https://halazon.hudhudclient.com/Api/"
    const val auth =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6OSwibmFtZSI6IlNhbWFyIEVsaWFzIiwicGhvbmUiOiIrOTYyNzkwOTYwMTQ0IiwiZW1haWwiOiJ0ZXN0QHRlc3QuY29tIiwiaW1hZ2UiOiIiLCJ2ZXJpZnllZCI6ZmFsc2UsImZjbV90b2tlbiI6ImRBanpwOHZhUWwtekFPRUJLVjZNNFY6QVBBOTFiSFpWbjdqWGFvUWxQQmRYSHZiOVJFUmJYYzd6RUU5NDVMVTlLdmkwbE5vUEFZS1U1X25aaU9vaGRJbW9sTVlhbnV4MFZaajRVSXdhUmxwUkhzN05Vbzh6LUpDOG5ndTg0YlJMdERKZUVWWEZiSmYzS2dTRTdLejJsMWpvZ3JLTHdUR2NydXgiLCJsYW5ndWFnZSI6ImVuIiwic3RhdHVzIjoiMCJ9.iYWCyYS4KcU4GHrhdaXajru65xIn1pY_aE_nK6yPMHI"
    const val debounceTimer = 1000L
    const val timeFormat = "yyyy-MM-dd"
}