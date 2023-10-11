package vef.ter.youtube.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.lifecycle.LiveData

@Suppress("DEPRECATION")
class Online(context: Context) : LiveData<Boolean>() {
    private var manager: ConnectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var managerCallback: ConnectivityManager.NetworkCallback
    override fun onActive() {
        super.onActive()
        updateConnect()
        manager.registerDefaultNetworkCallback(
            getManagerCallback()
        )
    }

    override fun onInactive() {
        super.onInactive()
        manager.unregisterNetworkCallback(managerCallback)
    }

    private fun updateConnect() {
        val activeNetwork: NetworkInfo? = manager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }

    private fun getManagerCallback(): ConnectivityManager.NetworkCallback {
        managerCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }
        }
        return managerCallback
    }
}