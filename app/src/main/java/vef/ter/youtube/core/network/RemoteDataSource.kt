package vef.ter.youtube.core.network

import vef.ter.youtube.BuildConfig
import vef.ter.youtube.core.base.BaseDataSource
import vef.ter.youtube.core.utils.Resource
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.utils.Constants

internal class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {
    suspend fun getPlaylists(): Resource<PlayListsModel> {
        return getResult {
            apiService.getPlayLists(
                part = Constants.PART,
                channelId = Constants.CHANNEL_ID,
                apiKey = BuildConfig.API_KEY,
                maxResult = 10,
            )
        }
    }
}