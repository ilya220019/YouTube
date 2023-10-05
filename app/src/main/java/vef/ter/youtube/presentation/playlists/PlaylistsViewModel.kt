package vef.ter.youtube.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vef.ter.youtube.core.base.BaseViewModel
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.domain.repository.Repository

internal class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {
    private val _playLists = MutableLiveData<PlayListsModel>()
    val playLists: LiveData<PlayListsModel> get() = _playLists

    fun getPlaylists()=
        doOperation(
            operation = {
                repository.getPlaylists()
            }, success = {_playLists.postValue(it)}
        )
}