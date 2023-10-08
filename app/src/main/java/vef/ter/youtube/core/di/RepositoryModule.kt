package vef.ter.youtube.core.di

import org.koin.dsl.module
import vef.ter.youtube.domain.repository.Repository

val repositoryModule = module {
    single { Repository(get()) }
}