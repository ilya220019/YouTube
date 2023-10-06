package vef.ter.youtube.presentation.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vef.ter.youtube.core.base.BaseViewModel
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.domain.repository.Repository

class DetailItemsViewModel(private val repository: Repository) : BaseViewModel() {

    private var _playlistItems = MutableLiveData<PlayListsModel>()
    val playlistItems: LiveData<PlayListsModel> get() = _playlistItems

    fun getPlaylistItems(playlistId: String) = doOperation(
        operation = { repository.getPlaylistItems(playlistId) },
        success = { _playlistItems.postValue(it) }
    )
}