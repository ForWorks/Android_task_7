package com.example.myapplication.presentation.ui.view

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.data.model.Post
import com.example.myapplication.RecyclerAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.di.App
import com.example.myapplication.presentation.ui.viewmodel.MainViewModel
import com.example.myapplication.presentation.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this, factory).get(MainViewModel::class.java) }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val dialog by lazy { AlertDialog.Builder(this).setCancelable(true).create() }
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        (application as App).getAppComponent().inject(this)

        if (viewModel.form.value == null) {
            CoroutineScope(Dispatchers.IO).launch {
                while (!isOnline()) delay(1000)
                getForm()
            }
        }

        viewModel.form.observe(this) {
            adapter = RecyclerAdapter(it.fields)
            binding.form.adapter = adapter
            Glide.with(this).load(it.image).into(binding.image)
            supportActionBar?.title = it.title
        }

        binding.submitButton.setOnClickListener {
            if(viewModel.form.value != null && isOnline())
                sendForm(adapter.response)
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.activeNetwork != null)
            return true
        return false
    }

    private fun getForm() {
        CoroutineScope(Dispatchers.IO).launch {
            runOnUiThread { binding.progressBar.visibility = View.VISIBLE }
            viewModel.getForm()
            runOnUiThread { binding.progressBar.visibility = View.INVISIBLE }
        }
    }

    private fun sendForm(response: Map<String, String>) {
        CoroutineScope(Dispatchers.IO).launch {
            runOnUiThread { binding.progressBar.visibility = View.VISIBLE }
            viewModel.sendForm(Post(response))
            runOnUiThread {
                binding.progressBar.visibility = View.INVISIBLE
                dialog.setMessage(viewModel.result.value?.result)
                dialog.show()
            }
        }
    }
}