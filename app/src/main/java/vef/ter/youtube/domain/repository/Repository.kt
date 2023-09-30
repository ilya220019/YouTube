package vef.ter.youtube.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vef.ter.youtube.BuildConfig
import vef.ter.youtube.core.network.ApiService
import vef.ter.youtube.core.utils.Resource
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.utils.Constants

internal class Repository(private val apiService: ApiService) {

    fun getPlaylists(): LiveData<Resource<PlayListsModel>> {
        val resourceData = MutableLiveData<Resource<PlayListsModel>>()
        apiService.getPlayLists(
            part = Constants.PART,
            channelId = Constants.CHANNEL_ID,
            apiKey = BuildConfig.API_KEY,
            maxResult = 10,
        )
            .enqueue(
            object : Callback<PlayListsModel> {

                override fun onResponse(
                    call: Call<PlayListsModel>,
                    response: Response<PlayListsModel>
                ) {
                    if (response.isSuccessful) {
                        resourceData.value = Resource.success(response.body())
                    } else {
                        resourceData.value = Resource.error(
                            msg = response.message().toString(),
                            data = null,
                            code = 429
                        )
                    }
                }


                override fun onFailure(call: Call<PlayListsModel>, t: Throwable) {
                    resourceData.value = Resource.error(
                        msg = t.message.toString(),
                        data = null,
                        code = 429
                    )
                }
            }
        )
        return resourceData
    }
}