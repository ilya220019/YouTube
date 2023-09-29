package vef.ter.youtube.core.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vef.ter.youtube.data.model.PlayListsModel

internal interface ApiService {
    @GET("playlists")
    fun getPlayLists(
        @Query("part")
        part: String,
        @Query("key")
        apiKey: String,
        @Query("channelId")
        channelId: String,
        @Query("maxResults")
        maxResult: Int

    ): Call<PlayListsModel>
}