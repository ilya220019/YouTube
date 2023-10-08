package vef.ter.youtube.core.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import vef.ter.youtube.data.model.PlayListsModel

interface ApiService {
    @GET("playlists")
    suspend fun getPlayLists(
        @Query("part")
        part: String,
        @Query("key")
        apiKey: String,
        @Query("channelId")
        channelId: String,
        @Query("maxResults")
        maxResult: Int

    ): Response<PlayListsModel>

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int
    ): Response<PlayListsModel>

    @GET("videos")
    suspend fun getVideo(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("id") videoId: String
    ): Response<PlayListsModel>

}