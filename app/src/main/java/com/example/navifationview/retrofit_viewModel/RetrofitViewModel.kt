package com.example.navifationview.retrofit_viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navifationview.Retrofit_Instance.RetrofitInstance
import com.example.navifationview.retrofit_data_model.RetrofitDataModel
import kotlinx.coroutines.launch

class RetrofitViewModel : ViewModel() {


    val phoneInfo: MutableLiveData<List<RetrofitDataModel.Product>> = MutableLiveData()



    fun getPhoneInfoList() = viewModelScope.launch {
        val response = RetrofitInstance.api.getItem()
        if (response.isSuccessful) {

            val data = response.body()
            phoneInfo.value = data?.products

//            val data = response.body()
//            phoneInfo.value = data?.products ?: emptyList()


        } else {
            val code = response.code()
            val error = response.errorBody().toString()
            Log.d("error_retrofit", "phoneInfoList: error: error code: $code, $error")
        }
    }

//    fun getPaginatedData() {
//        androidx.paging.Pager(
//            config = PagingConfig(
//                pageSize = 10,
//            ),
//            pagingSourceFactory = { PagingSource() }
//        ).flow
//    }

//    fun getPagination() = viewModelScope.launch {
//        val response = RetrofitInstance.api.getPhones()
//        if(response.isSuccessful){
//
//            val data = response.body()
//            phoneInfo.value = data?.pr
//
//        }
//
//    }

}