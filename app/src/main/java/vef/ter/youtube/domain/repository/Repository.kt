package vef.ter.youtube.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import vef.ter.youtube.core.network.RemoteDataSource
import vef.ter.youtube.core.utils.Resource
import vef.ter.youtube.data.model.PlayListsModel

internal class Repository(private val remoteDataSource: RemoteDataSource) {

    fun getPlaylists(): LiveData<Resource<PlayListsModel>> {
        return liveData {
            emit(Resource.loading())
            emit(remoteDataSource.getPlaylists())
        }
    }
}