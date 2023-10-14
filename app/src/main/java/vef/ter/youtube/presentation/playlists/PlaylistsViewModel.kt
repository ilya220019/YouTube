package vef.ter.youtube.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import vef.ter.youtube.core.base.BaseViewModel
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.domain.repository.Repository

class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPagingPlaylists(): LiveData<PagingData<PlayListsModel.Item>> {
        return repository.getPlaylists()

    }

}