package com.medical.ecommercetask.ui.home


import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sa.flexifleet.data.model.Mission
import com.sa.flexifleet.data.model.MissionSummary
import com.sa.flexifleet.databinding.TasksBinding
import com.sa.flexifleet.ui.base.BaseActivity
import com.sa.flexifleet.util.apiStatus.RequestStatus
import com.sa.flexifleet.util.helper.TasksOperations
import com.sa.flexifleet.util.helper.openLink
import com.sa.flexifleet.util.snackbar.customSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : Fragment(){
    private val tasksSummaryAdapter: TasksSummaryAdapter by lazy {
        TasksSummaryAdapter(this@Home)
    }
    private lateinit var binding: TasksBinding
    private lateinit var viewModel: TasksViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = TasksBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Any?>("key_result")
            ?.observe(viewLifecycleOwner) {
                viewModel.getData()
            }
        operations = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TasksAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val total = layoutManager.itemCount
                val currentLastItem = layoutManager.findLastVisibleItemPosition()
                if (total < totalRows && currentLastItem == total.minus(1) && !isLoading) {
                    isLoading = true
                    viewModel.getNextPage()
                }
            }
        })
        binding.add.setOnClickListener {
            findNavController().navigate(TasksDirections.actionTasksToAddTask(null))
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            val filter = tasksSummaryAdapter.getSelected()
            if (filter != null) {
                select(filter)
            } else {
                viewModel.missions()
            }
            binding.recyclerView.postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
            }, 2000)
        }
        lifecycleScope.launch {
            viewModel.missionsRes.collect {
                when (it) {
                    is RequestStatus.Idle -> {
                        BaseActivity.progress.hide()
                    }

                    is RequestStatus.Loading -> {
                        BaseActivity.progress.show()
                    }

                    is RequestStatus.Success -> {
                        BaseActivity.progress.hide()
                        val data = it.data.result
                        if (isLoading) {
                            viewModel.addData(data.missions.data)
                        } else {
                            viewModel.setData(data.missions.data)
                        }
                        val b = binding.recyclerView5.adapter == null
                        val summaryList = data.summary
                        binding.recyclerView5.adapter = tasksSummaryAdapter
                        tasksSummaryAdapter.submitList(summaryList)
                        if (b && summaryList.isNotEmpty()) {
                            select(summaryList[0])
                        } else if (b) {
                            tasksSummaryAdapter.setSelected(null)
                            viewModel.missionsFilter(listOf(), listOf())
                        }
                        totalRows = data.missions.pagination.total
                        isLoading = false
                    }

                    is RequestStatus.Failure -> {
                        BaseActivity.progress.hide()
                        Snackbar.make(binding.root, it.data.message, Snackbar.LENGTH_SHORT).show()
                    }

                    is RequestStatus.NetworkLost -> {
                        BaseActivity.progress.networkLost()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.errorRes.collect {
                when (it) {
                    is RequestStatus.Idle -> {
                        BaseActivity.progress.hide()
                    }

                    is RequestStatus.Loading -> {
                        BaseActivity.progress.show()
                    }

                    is RequestStatus.Success -> {
                        BaseActivity.progress.hide()
                    }

                    is RequestStatus.Failure -> {
                        BaseActivity.progress.hide()
                        customSnackBar(requireContext(), binding.root, it.data.message, false)
                        val url = it.data.result?.url ?: ""
                        if (url.isNotEmpty() && Patterns.WEB_URL.matcher(url).matches()) {
                            lifecycleScope.launch {
                                delay(2000)
                                openLink(requireContext(), url)
                            }
                        }
                    }

                    is RequestStatus.NetworkLost -> {
                        BaseActivity.progress.networkLost()
                    }
                }
            }
        }
        viewModel.missionList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.noData = it.isEmpty()
        }
    }
}
