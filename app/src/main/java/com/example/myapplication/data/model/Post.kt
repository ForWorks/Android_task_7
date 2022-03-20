package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("form")
    var form: Map<String, String?>
)