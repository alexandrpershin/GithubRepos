package com.alexpershin.githubrepos.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.RuntimeException

/**
 * This is singleton class responsible for providing information about internet connectivity
 * */

class NetworkUtils private constructor() {

    private var context: Context? = null
    private var isNetworkConnected: Boolean = false
    private var isInitialized: Boolean = false
    private var connectivityManager: ConnectivityManager? = null
    private val TAG = this.javaClass.canonicalName

    /**
     * Call [initialize] in your Application class for setting up context
     * */

    fun initialize(context: Context) {
        if (isInitialized) {
            throw RuntimeException("The NetworkUtils is already initialized")
        } else {
            this.context = context
            this.isInitialized = true
            this.connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                registerNetworkChangeCallback()
            }
        }
    }

    fun isNetworkConnected(): Boolean {
        require(isInitialized) { "NetworkUtils.initialize() method should be called first" }

        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            isNetworkConnectedPreNougat()
        } else {
            isNetworkConnected
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun registerNetworkChangeCallback() {
        connectivityManager?.registerDefaultNetworkCallback(networkChangeCallback)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun unregisterNetworkChangeCallback() {
        try {
            connectivityManager?.unregisterNetworkCallback(networkChangeCallback)
        }catch (ex : java.lang.Exception){
            Log.i(TAG, ex.message.toString())
        }
    }

    /**
     * [networkChangeCallback] callback is used for getting info about internet connectivity for Android API >= [Build.VERSION_CODES.N]
     * */

    private val networkChangeCallback = object :
        ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            isNetworkConnected = true
        }

        override fun onLost(network: Network?) {
            isNetworkConnected = false
        }
    }

    /**
     * This method is used for getting info about internet connectivity for Android API < [Build.VERSION_CODES.N]
     * */

    private fun isNetworkConnectedPreNougat(): Boolean {
        val connectivityManager: ConnectivityManager? =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        var activeNetwork: NetworkInfo? = null
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.activeNetworkInfo
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }


    companion object {
        val instance: NetworkUtils by lazy {
            NetworkUtils()
        }
    }


}



