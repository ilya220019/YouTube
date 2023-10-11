package vef.ter.youtube.domain.repository

import vef.ter.youtube.core.network.RemoteDataSource
import vef.ter.youtube.data.model.PlayListsModel

class Repository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getPlaylists(): Result<PlayListsModel> {
        return remoteDataSource.getPlaylists()
    }
    suspend fun getPlaylistItems(playlistId: String): Result<PlayListsModel> {
        return remoteDataSource.getPlaylistItems(playlistId)
    }
    suspend fun getVideo(videoId: String): Result<PlayListsModel> {
        return remoteDataSource.getVideo(videoId)
    }

}