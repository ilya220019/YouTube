package vef.ter.youtube.domain.repository

import androidx.lifecycle.LiveData
import vef.ter.youtube.core.network.RemoteDataSource
import vef.ter.youtube.data.model.PlayListsModel

internal class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getPlaylists(): Result<PlayListsModel> {
        return remoteDataSource.getPlaylists()
    }
}