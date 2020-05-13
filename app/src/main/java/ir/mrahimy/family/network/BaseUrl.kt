package ir.mrahimy.family.network

import ir.mrahimy.family.BuildConfig

object BaseUrl {

    object Karafs {
        private const val ENDPOINT_VERSION = "" // we can change endpoint version
        const val BASE_URL_RELEASE = "http://karafsapp.com/android-test/$ENDPOINT_VERSION"
        const val BASE_URL_DEBUG = "http://karafsapp.com/android-test/$ENDPOINT_VERSION"
    }
}

fun getBaseUrl(): String {
    return if (BuildConfig.DEBUG) BaseUrl.Karafs.BASE_URL_DEBUG
    else BaseUrl.Karafs.BASE_URL_RELEASE
}