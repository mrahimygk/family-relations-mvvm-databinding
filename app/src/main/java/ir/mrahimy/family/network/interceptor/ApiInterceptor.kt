package ir.mrahimy.family.network.interceptor

import com.google.gson.Gson
import ir.mrahimy.family.network.error.ApiException
import ir.mrahimy.family.network.error.NETWORK_ERROR
import okhttp3.Interceptor
import okhttp3.Response
import java.net.UnknownHostException

const val NO_AUTHENTICATION = "NO-AUTHENTICATION"

/**
 * Intercepts api call and checks for AUTHENTICATION type.
 *      if needs to add token, gets it from sharedPrefs.
 *
 * Adds 'Android Native Client' as user agent
 *
 */
class ApiInterceptor(
    private val gson: Gson
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val hasToken = chain.request().header(NO_AUTHENTICATION)
        //we can add token here but karafs does not provide it
        //.addHeader(AUTHORIZATION_HEADER, "Bearer $token")

        try {
            val response = chain.proceed(requestBuilder.build())

            // Check if response is video/mpeg then don't convert it to obj
            if (response.header("Content-Type") == "video/mpeg")
                return response

            //if api has other data besides the data class (error, message)
            //we extract them here, but karafs test does not need this
            //response.newBuilder().body(body).build()
            return response
        } catch (e: UnknownHostException) {
            throw e
        } catch (e: ApiException) {
            throw e
        } catch (e: Exception) {
            throw ApiException(NETWORK_ERROR, e)
        }
    }
}