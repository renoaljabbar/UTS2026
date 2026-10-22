package com.example.projectuts.response

import com.example.projectuts.model.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products") val products: List<Product>,
    @SerializedName("total") val total: Int
)