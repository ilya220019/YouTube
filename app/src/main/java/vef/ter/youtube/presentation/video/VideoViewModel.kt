package com.example.youtube57.presentation.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vef.ter.youtube.core.base.BaseViewModel
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.domain.repository.Repository

class VideoViewModel(private val repository: Repository) : BaseViewModel() {
    private var _video = MutableLiveData<PlayListsModel>()
    val video: LiveData<PlayListsModel> get() = _video

    fun getVideo(videoId: String) = doOperation(
        operation = { repository.getVideo(videoId) },
        success = { _video.postValue(it) }
    )
}