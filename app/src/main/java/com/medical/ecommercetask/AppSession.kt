package com.medical.ecommercetask

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.medical.ecommercetask.util.dataStore.Locality
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AppSession : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    companion object {
        lateinit var currentLang: String
    }


    override fun onCreate() {
        super.onCreate()
        currentLang = Locality().getLocale(context = this).language
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()
}