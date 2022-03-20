package com.example.myapplication.presentation.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Form
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.Result
import com.example.myapplication.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val form: MutableLiveData<Form> = MutableLiveData()
    val result: MutableLiveData<Result> = MutableLiveData()

    suspend fun getForm() {
        val layout = repository.getForm()
        withContext(Dispatchers.Main){ form.value = layout }
    }

    suspend fun sendForm(body: Post) {
        val response = repository.sendForm(body)
        withContext(Dispatchers.Main){ result.value = response }
    }
}