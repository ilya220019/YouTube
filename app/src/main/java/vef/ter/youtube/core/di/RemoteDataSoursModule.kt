package vef.ter.youtube.core.di


import org.koin.dsl.module
import vef.ter.youtube.core.network.RemoteDataSource

val remoteDataSource = module {
    single { RemoteDataSource(get()) }
}