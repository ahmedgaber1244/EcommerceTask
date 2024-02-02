package com.ecommerce.task.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ecommerce.task.AppSession.Companion.currentLang
import com.ecommerce.task.R
import com.ecommerce.task.databinding.ActivityBaseBinding
import com.ecommerce.task.util.dataStore.Locality
import com.ecommerce.task.util.helper.ProgressOperations
import com.ecommerce.task.util.snackbar.customSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class BaseActivity : AppCompatActivity(), ProgressOperations {
    companion object {
        lateinit var progress: ProgressOperations
    }

    private lateinit var viewModel: BaseViewModel
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocality(currentLang)
        viewModel = ViewModelProvider(this)[BaseViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        lifecycleScope.launch {
            viewModel.locality.collect {
                Locality().setLocale(this@BaseActivity, it)
            }
        }
        progress = this
        fullscreen()
        hideProgress()
    }

    private fun fullscreen() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = resources.getColor(android.R.color.transparent)
    }

    private fun loadLocality(s: String) {
        val config = resources.configuration
        val locale = Locale(s)
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun showProgress() {
        binding.showLoading = true
        binding.executePendingBindings()
    }

    private fun hideProgress() {
        binding.showLoading = false
        binding.executePendingBindings()
    }

    private fun showNetworkLost() {
        hideProgress()
        customSnackBar(this, binding.root, resources.getString(R.string.networkLost), false)
    }


    override fun show() {
        showProgress()
    }

    override fun hide() {
        hideProgress()
    }

    override fun networkLost() {
        showNetworkLost()
    }
}