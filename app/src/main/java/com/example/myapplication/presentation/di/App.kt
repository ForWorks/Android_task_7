package com.example.myapplication.presentation.di

import android.app.Application
import com.example.myapplication.presentation.di.components.AppComponent
import com.example.myapplication.presentation.di.components.DaggerAppComponent

class App: Application() {

    private val component by lazy { DaggerAppComponent.create() }

    fun getAppComponent(): AppComponent = component
}