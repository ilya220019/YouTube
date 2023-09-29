package com.slottica.reviewfueatures.youtube57_3.presetation.playlists

import androidx.lifecycle.LiveData
import vef.ter.youtube.core.base.BaseViewModel
import vef.ter.youtube.core.utils.Resource
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.domain.repository.Repository

internal class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPlaylists(): LiveData<Resource<PlayListsModel>> {
        return repository.getPlaylists()
    }
}