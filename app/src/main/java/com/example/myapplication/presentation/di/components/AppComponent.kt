package com.example.myapplication.presentation.di.components

import com.example.myapplication.presentation.di.modules.NetworkModule
import com.example.myapplication.presentation.di.modules.ViewModelModule
import com.example.myapplication.presentation.ui.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}