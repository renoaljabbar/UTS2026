package com.example.projectuts.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("price") val price: Double,
    @SerializedName("discountPercentage") val discountPercentage: Double,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stock") val stock: Int,
    @SerializedName("brand") val brand: String? = null,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("availabilityStatus") val availabilityStatus: String? = null,
    @SerializedName("warrantyInformation") val warrantyInformation: String? = null,
    @SerializedName("shippingInformation") val shippingInformation: String? = null,
    @SerializedName("returnPolicy") val returnPolicy: String? = null,
    @SerializedName("tags") val tags: List<String>? = null,
    @SerializedName("dimensions") val dimensions: Dimensions? = null,
    @SerializedName("reviews") val reviews: List<Review>? = null
)
data class Dimensions(
    @SerializedName("width") val width: Double? = null,
    @SerializedName("height") val height: Double? = null,
    @SerializedName("depth") val depth: Double? = null
)
data class Review(
    @SerializedName("rating") val rating: Int? = null,
    @SerializedName("comment") val comment: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("reviewerName") val reviewerName: String? = null,
    @SerializedName("reviewerEmail") val reviewerEmail: String? = null
)



