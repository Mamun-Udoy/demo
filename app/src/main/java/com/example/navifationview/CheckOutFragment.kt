package com.example.navifationview

import CheckoutViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navifationview.adapter.ThirdAdapter
import com.example.navifationview.checkout_database.CheckOutItem
import com.example.navifationview.checkout_database.CheckOutItemInsertViewModel
import com.example.navifationview.databinding.FragmentCheckOutBinding
import com.example.navifationview.retrofit_data_model.RetrofitDataModel
import com.example.navifationview.userViewModel.UserViewModel


class CheckOutFragment : Fragment(), ThirdAdapter.ItemClickListener {

    private val thirdAdapter: ThirdAdapter by lazy { ThirdAdapter(arrayListOf(), this) }
    private lateinit var binding: FragmentCheckOutBinding
    private val viewModel by viewModels<UserViewModel>()
    private val viewModel2 by viewModels<UserViewModel>()

    private val   viewModelCheckOutItemDeleteViewModel by activityViewModels<CheckOutItemInsertViewModel>()


    private fun init() {

        binding.recyclerview4.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview4.adapter = thirdAdapter



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckOutBinding.inflate(inflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        getData()
        binding.button.setOnClickListener {
            viewModel2.deleteAllCheckoutItems(
                context = requireContext()
            )
            getData()
            val updatedSize = context?.let { it1 -> viewModel2.getCheckoutItemsSize(it1) }
            if (updatedSize != null) {
                viewModelCheckOutItemDeleteViewModel.updateDatabaseSize(updatedSize)
            }
        }


    }

    private fun getData() {
        val data = viewModel.readCheckoutItem(requireContext())
        Log.d("adapterGetData", "get the data from retro $data")
        thirdAdapter.updateList(data)


    }


    override fun onItemDeleted(item: CheckOutItem, position: Int) {
        viewModel2.deleteChekcoutItem(
            checkOutItem = item,
            context = requireContext()
        )
        getData()
        val updatedSize = context?.let { it1 -> viewModel2.getCheckoutItemsSize(it1) }
        if (updatedSize != null) {
            viewModelCheckOutItemDeleteViewModel.updateDatabaseSize(updatedSize)
        }

    }

    private fun getCheckoutItem(item: RetrofitDataModel.Product): CheckOutItem {
        return CheckOutItem(
            itemId = item.id,
            discount = item.discount,
            rating = item.rating,
            stock = item.stock,
            brand = item.brand,
            category = item.categories,
        )
    }




}


