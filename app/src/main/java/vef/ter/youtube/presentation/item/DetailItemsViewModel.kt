package vef.ter.youtube.presentation.item

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import vef.ter.youtube.core.base.BaseViewModel
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.domain.repository.Repository

class DetailItemsViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPagingPlaylists(playlistId: String): LiveData<PagingData<PlayListsModel.Item>> {
        return repository.getPlaylistItems(playlistId)

    }

}