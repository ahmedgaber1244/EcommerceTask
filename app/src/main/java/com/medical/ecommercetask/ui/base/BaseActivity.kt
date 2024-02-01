package com.medical.ecommercetask.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.medical.ecommercetask.AppSession.Companion.currentLang
import com.medical.ecommercetask.R
import com.medical.ecommercetask.databinding.ActivityBaseBinding
import com.medical.ecommercetask.util.dataStore.Locality
import com.medical.ecommercetask.util.helper.ProgressOperations
import com.medical.ecommercetask.util.snackbar.customSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class BaseActivity : AppCompatActivity(), ProgressOperations {
    companion object {
        lateinit var progress: ProgressOperations
        lateinit var token: String
        lateinit var userType: String
        lateinit var userEmail: String
        lateinit var userName: String
        lateinit var userPhone: String
        var userId: Int = 0
        var state: Boolean = false
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
        lifecycleScope.launch {
            viewModel.nightMode.collect {
                loadNightMode(it)
            }
        }
        lifecycleScope.launch {
            viewModel.token.collect {
                token = it
            }
        }
        lifecycleScope.launch {
            viewModel.userType.collect {
                userType = it
            }
        }
        lifecycleScope.launch {
            viewModel.userEmail.collect {
                userEmail = it
            }
        }
        lifecycleScope.launch {
            viewModel.state.collect {
                state = it
            }
        }
        lifecycleScope.launch {
            viewModel.userId.collect {
                userId = it
            }
        }
        lifecycleScope.launch {
            viewModel.userName.collect {
                userName = it
            }
        }
        lifecycleScope.launch {
            viewModel.userPhone.collect {
                userPhone = it
            }
        }
        fullscreen()
        hideProgress()
        progress = this
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

    private fun loadNightMode(i: Int) {
        when (i) {
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            3 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun showProgress() {
        binding.showLoading = true
    }

    private fun hideProgress() {
        binding.showLoading = false
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