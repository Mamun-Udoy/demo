package com.example.navifationview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navifationview.paging.PagingAdapter
import com.example.navifationview.databinding.FragmentPaginationBinding
import com.example.navifationview.paging.LoaderAdapter
import com.example.navifationview.paging.PagingViewModel
import com.example.navifationview.retrofit_viewModel.RetrofitViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


class PaginationFragment : Fragment() {

    private  lateinit var binding:FragmentPaginationBinding
    private val myAdapter: PagingAdapter by lazy { PagingAdapter() }
    private val viewModel by viewModels<PagingViewModel>()
    private val viewModel2 by viewModels<RetrofitViewModel>()

    private fun init() {
        binding.recyclerview4.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaginationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observer()
        viewModel2.getPhoneInfoList()

    }
    private fun observer() {

        val pagingData = viewModel.getData().distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collect {
                myAdapter.submitData(it)
            }
        }
    }


}