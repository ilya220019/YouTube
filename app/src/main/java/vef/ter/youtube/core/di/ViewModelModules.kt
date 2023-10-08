package vef.ter.youtube.core.di


import vef.ter.youtube.presentation.video.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vef.ter.youtube.presentation.item.DetailItemsViewModel
import vef.ter.youtube.presentation.playlists.PlaylistsViewModel

val viewModelModule = module {
    viewModel { PlaylistsViewModel(get()) }
    viewModel { DetailItemsViewModel(get()) }
    viewModel { VideoViewModel(get()) }
}