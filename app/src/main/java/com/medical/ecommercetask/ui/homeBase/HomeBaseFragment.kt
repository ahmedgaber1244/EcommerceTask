package com.medical.ecommercetask.ui.homeBase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.medical.ecommercetask.R
import com.medical.ecommercetask.databinding.HomeBaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeBaseFragment : Fragment() {
    private lateinit var binding: HomeBaseBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = HomeBaseBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        return binding.root
    }
}
