package ir.mrahimy.family.network.error

import ir.mrahimy.family.R
import java.net.UnknownHostException

/**
 * Maps [ApiException] status code to actual string from strings.xml.
 */
fun handleError(e: Throwable): Int {
    return when (e) {
        is ApiException -> {
            when (e.statusCode) {
                INTERNAL_SERVER_ERROR -> R.string.network_error
                else -> R.string.unknown_error
            }
        }
        is UnknownHostException -> {
            R.string.internet_not_available
        }
        else -> {
            R.string.unknown_error
        }
    }
}