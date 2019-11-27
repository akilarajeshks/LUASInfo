@file:Suppress("DEPRECATION")

package com.zestworks.luasinfo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val module = module {
            factory { provideRetrofit() }
            single { provideNetworkService(get()) }
            single<Repository> { LUASInfoRepository(get()) }
            viewModel { LUASInfoViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(module)
        }
    }

    private fun provideNetworkService(retrofit: Retrofit): LUASInfoService {
        return retrofit.create(LUASInfoService::class.java)
    }

    private fun provideRetrofit(): Retrofit {

        val baseUrl = "http://luasforecasts.rpa.ie/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }
}