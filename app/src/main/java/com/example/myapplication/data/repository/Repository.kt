package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Form
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.Result
import com.example.myapplication.data.service.Service
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: Service) {

    suspend fun getForm(): Form? {
        return apiService.getForm().body()
    }

    suspend fun sendForm(post: Post): Result? {
        return apiService.sendForm(post).body()
    }
}