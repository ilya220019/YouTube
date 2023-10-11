package vef.ter.youtube.core.di

import org.koin.dsl.module
import vef.ter.youtube.core.network.provideApiService
import vef.ter.youtube.core.network.provideInterceptor
import vef.ter.youtube.core.network.provideOkHttpClient
import vef.ter.youtube.core.network.provideRetrofitClient

val networkMode = module {
    single { provideInterceptor() }
    single { provideOkHttpClient(get()) }
    factory { provideRetrofitClient(get()) }
    single { provideApiService(get()) }
}