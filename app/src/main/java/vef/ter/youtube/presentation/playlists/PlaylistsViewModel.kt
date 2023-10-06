package vef.ter.youtube.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vef.ter.youtube.core.base.BaseViewModel
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.domain.repository.Repository

class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {
    private var _playlists = MutableLiveData<PlayListsModel>()
    val playlists: LiveData<PlayListsModel> get() = _playlists

    fun getPlaylists() = doOperation(
        operation = { repository.getPlaylists() },
        success = { _playlists.postValue(it) }
    )
}