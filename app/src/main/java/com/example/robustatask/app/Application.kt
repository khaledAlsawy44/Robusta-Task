package com.example.robustatask.app

import android.app.Application
import com.example.robustatask.di.homeModule
import com.example.robustatask.di.networkModule
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

@FlowPreview
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    networkModule,
                    homeModule
                )
            )
        }
    }
}
