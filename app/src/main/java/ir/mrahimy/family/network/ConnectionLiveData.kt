package ir.mrahimy.family.network

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData

/**
 * A singleton to contain the Connectivity status of application.
 * It provides the status as a live data to be observed inside the view by ConnectionLiveData.observe
 *
 * Since this live data is observed in [BaseActivity][ir.mrahimy.family.base.BaseActivity]
 * you can use ConnectionLiveData.value to access the updated ConnectivityModel
 *
 */
object ConnectionLiveData : LiveData<ConnectivityModel>() {

    private lateinit var appContext: Context

    private lateinit var connectivityManager: ConnectivityManager

    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    fun init(context: Context) {
        appContext = context.applicationContext
        connectivityManager =
            appContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onActive() {
        super.onActive()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
                connectivityManager.registerDefaultNetworkCallback(getConnectivityManagerCallback())
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> lollipopNetworkAvailableRequest()
            else -> throw  RuntimeException("We do not support sdk < LOLLIPOP")
        }
    }

    override fun onInactive() {
        super.onInactive()
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
            } else {
                appContext.unregisterReceiver(networkReceiver)
            }
        } catch (e: Exception) {

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkAvailableRequest() {
        val builder = NetworkRequest.Builder()
            .addTransportType(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(android.net.NetworkCapabilities.TRANSPORT_WIFI)
        connectivityManager.registerNetworkCallback(
            builder.build(),
            getConnectivityManagerCallback()
        )
    }

    /**
     * [connectivityManagerCallback] is initialized for SDK_INT >= LOLLIPOP
     * lollipop needs a NetworkRequest.Builder but nougat and later does not need it
     */
    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network?) {
                    postValue(ConnectivityModel(1, true))
                }

                override fun onLost(network: Network?) {
                    postValue(ConnectivityModel(1, false))
                }
            }
            return connectivityManagerCallback
        } else {
            throw IllegalAccessError("Should not happened")
        }
    }

    /**
     * This BroadcastReceiver will be registered only on SDK_INT < LOLLIPOP
     */
    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            throw Exception("cannot happen")
        }
    }
}

data class ConnectivityModel(
    val type: Int,
    val isConnected: Boolean
)