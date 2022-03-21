package com.example.myapplication.data.service

import com.example.myapplication.domain.utils.Constants.GET_ENDPOINT
import com.example.myapplication.domain.utils.Constants.POST_ENDPOINT
import com.example.myapplication.data.model.Form
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.Result
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {
    @GET(GET_ENDPOINT)
    suspend fun getForm(): Response<Form>

    @POST(POST_ENDPOINT)
    suspend fun sendForm(@Body post: Post): Response<Result>
}