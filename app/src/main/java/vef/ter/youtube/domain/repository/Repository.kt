package vef.ter.youtube.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import vef.ter.youtube.core.network.RemoteDataSource
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.domain.paging.PagingSource
import vef.ter.youtube.domain.paging.PagingSourceDetail

class Repository(private val remoteDataSource: RemoteDataSource) {
    fun getPlaylists(): LiveData<PagingData<PlayListsModel.Item>> {
        val pagingData = Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = Int.MAX_VALUE,
                enablePlaceholders = true,
                prefetchDistance = 5
            ), pagingSourceFactory = { PagingSource(remoteDataSource) }
        )
        return pagingData.liveData
    }

    fun getPlaylistItems(playlistId: String): LiveData<PagingData<PlayListsModel.Item>> {
        val pagingData = Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = Int.MAX_VALUE,
                enablePlaceholders = true,
                prefetchDistance = 5

            ), pagingSourceFactory = { PagingSourceDetail(remoteDataSource, playlistId) }
        )
        return pagingData.liveData
    }

    suspend fun getVideo(videoId: String): Result<PlayListsModel> {
        return remoteDataSource.getVideo(videoId)
    }

}