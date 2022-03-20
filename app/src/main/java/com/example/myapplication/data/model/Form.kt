package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class Form(
    @SerializedName("title")
    var title: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("fields")
    var fields: List<Field> = arrayListOf()
)

data class Field(
    @SerializedName("title")
    var title: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("values")
    val values: Map<String, String>?
)