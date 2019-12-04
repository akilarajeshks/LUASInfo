@file:Suppress("DEPRECATION")

package com.zestworks.luasinfo

import android.app.Application
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import com.zestworks.luasinfo.listing.ListingRepository
import com.zestworks.luasinfo.listing.ListingService
import com.zestworks.luasinfo.listing.ListingViewModel
import com.zestworks.luasinfo.listing.NetworkListingRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit

class LuasInfoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val module = module {
            factory { provideRetrofit() }
            single { provideNetworkService(get()) }
            single<ListingRepository> {
                NetworkListingRepository(
                    get()
                )
            }
            viewModel { ListingViewModel(get()) }
        }
        startKoin {
            androidContext(this@LuasInfoApplication)
            modules(module)
        }
    }

    private fun provideNetworkService(retrofit: Retrofit): ListingService {
        return retrofit.create(ListingService::class.java)
    }

    private fun provideRetrofit(): Retrofit {
        val baseUrl = "http://luasforecasts.rpa.ie/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder().exceptionOnUnreadXml(
                        false
                    ).build()
                )
            )
            .build()
    }
}