package com.example.projectuts.retrofit

import com.example.projectuts.response.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    fun getProducts(@Query("limit") limit: Int): Call<ProductResponse>
}