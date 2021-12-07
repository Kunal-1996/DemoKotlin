package com.example.demokotlin.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NetworkUtils {

    private val networkLiveData: MutableLiveData<Boolean> = MutableLiveData(false)


    @RequiresApi(Build.VERSION_CODES.N)
    fun getNetworkLiveData(context: Context): LiveData<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                networkLiveData.postValue(true)
            }

            override fun onLost(network: Network) {
                networkLiveData.postValue(false)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        return networkLiveData
    }
}
