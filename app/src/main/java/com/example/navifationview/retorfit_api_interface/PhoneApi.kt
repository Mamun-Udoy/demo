package com.example.navifationview.retorfit_api_interface

import com.example.navifationview.retrofit_data_model.RetrofitDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhoneApi {

    @GET("/products")
    suspend fun getPhones(@Query("limit") limit: Int = 5): Response<List<RetrofitDataModel.Product>>

//    @GET("/products")
//    suspend fun getItem(
//        @Query("page") page: Int = 0
//    ): Response<RetrofitDataModel>

    @GET("/products")
    suspend fun getItem(
        @Query("limit") limit: Int = 0
    ): Response<RetrofitDataModel>


}