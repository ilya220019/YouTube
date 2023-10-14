package vef.ter.youtube.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import vef.ter.youtube.core.network.RemoteDataSource
import vef.ter.youtube.data.model.PlayListsModel

class PagingSourceDetail(private val remoteDataSource: RemoteDataSource, var playListId: String) :
    PagingSource<String, PlayListsModel.Item>() {
    override fun getRefreshKey(state: PagingState<String, PlayListsModel.Item>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PlayListsModel.Item> {
        try {
            val nextPageToken = params.key ?: ""
            val response = remoteDataSource.getPlaylistItems(playListId, nextPageToken)
            val items = mutableListOf<PlayListsModel.Item>()
            when {
                response.isSuccess -> response.onSuccess {
                    items.addAll(it.items)
                }

                response.isFailure -> response.onFailure {

                }
            }
            var nextKey = ""
            if (response.isSuccess) response.onSuccess { nextKey = it.nextPageToken }
            return LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

}