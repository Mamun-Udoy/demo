package com.example.navifationview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navifationview.adapter.SecondAdapter
import com.example.navifationview.checkout_database.CheckOutItem
import com.example.navifationview.checkout_database.CheckOutItemInsertViewModel
import com.example.navifationview.databinding.BottomSheetBinding
import com.example.navifationview.databinding.FragmentRetrofitBinding
import com.example.navifationview.retrofit_data_model.RetrofitDataModel
import com.example.navifationview.retrofit_viewModel.RetrofitViewModel
import com.example.navifationview.userViewModel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class RetrofitFragment : Fragment(), SecondAdapter.ItemClickCallback {

    lateinit var binding: FragmentRetrofitBinding
    private val viewModel by viewModels<RetrofitViewModel>()
    private val myAdapter: SecondAdapter by lazy { SecondAdapter(this) }

//    val thirdAdpter: ThirdAdapter by lazy { ThirdAdapter(arrayListOf()) }

    private val viewModel2 by viewModels<UserViewModel>()

    private val   viewModelCheckOutItemInsertViewModel by activityViewModels<CheckOutItemInsertViewModel>() // sharedViewModel



//    private val myAdapter2: ThirdAdapter by lazy { ThirdAdapter() }
    val phoneInfo: MutableLiveData<List<RetrofitDataModel.Product>> = MutableLiveData()

//    private var checkoutClickListener: CheckoutClickListener? = null


    private fun init() {
        binding.recyclerview3.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRetrofitBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observer()
        dbObserver()
        viewModel.getPhoneInfoList()

    }
    private fun dbObserver(){

    }

    private fun observer() {
        viewModel.phoneInfo.observe(viewLifecycleOwner) {
            val value = Gson().toJson(it)
            myAdapter.updateList(it)
            Log.d("data_value", "observer: adater data size: ${myAdapter.itemCount}")
            Log.d("data_value", "api value = $value")

        }
    }

    override fun onItemClicked(item: RetrofitDataModel.Product) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet, null)
        // this is a way to bind the bottomsheet xml in a fragment
        val bottomSheetBinding = BottomSheetBinding.bind(view)

        // Set up your bottom sheet with the inflated view
        val bsDialog = BottomSheetDialog(requireContext())
        bsDialog.setContentView(bottomSheetBinding.root)
//        setupCarousel(item.thumbnail)

        // Show the bottom sheet
        bsDialog.show()

        val carousel: ImageCarousel = bottomSheetBinding.carousel
        carousel.registerLifecycle(lifecycle)
        val list = mutableListOf<CarouselItem>()

// Image URL with caption


        for (item in item.images!!) {
            list.add(
                CarouselItem(
                    imageUrl = item,
                )
            )
        }
        carousel.setData(list)

        bottomSheetBinding.discount.text = item.discount.toString()
        bottomSheetBinding.brand.text = item.brand.toString()
        bottomSheetBinding.categories.text = item.categories.toString()
        bottomSheetBinding.stock.text = item.stock.toString()
        bottomSheetBinding.rating.text = item.rating.toString()

        bottomSheetBinding.dropArrow.setOnClickListener {
            bsDialog.dismiss()
            Toast.makeText(context, "clicked drop button", Toast.LENGTH_SHORT).show()
        }
        bottomSheetBinding.checkout.setOnClickListener {

            Toast.makeText(context, "clicked checkout", Toast.LENGTH_SHORT).show()

            //inserting checkout item into database
            val value = viewModel2.insertCheckoutItem(
                checkOutItem = getCheckoutItem(item),
                context = requireContext()
            )
            val updatedSize = context?.let { it1 -> viewModel2.getCheckoutItemsSize(it1) }
            if (updatedSize != null) {
                viewModelCheckOutItemInsertViewModel.updateDatabaseSize(updatedSize)
            }

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

//    interface CheckoutClickListener {
//
//        fun onCheckoutClicked()
//    }
//



}