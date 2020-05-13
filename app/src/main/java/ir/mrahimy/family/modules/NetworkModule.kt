package ir.mrahimy.family.modules

import com.google.gson.GsonBuilder
import ir.mrahimy.family.BuildConfig
import ir.mrahimy.family.network.AnnotationExclusionStrategy
import ir.mrahimy.family.network.getBaseUrl
import ir.mrahimy.family.network.interceptor.ApiInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

private const val CACHE_NAME = "network_cache"
private const val CACHE_SIZE = 30 * 1024 * 1024L // 10MB

// Default timeout -> sec
const val TIMEOUT_DEBUG = 60L
const val TIMEOUT_RELEASE = 20L


/**
 *Qualifiers to detect which dependency to be used (Like Dagger2 Qualifiers)
 */

object LoggingInterceptorQualifier : Qualifier

object ApiInterceptorQualifier : Qualifier

object CacheFileQualifier : Qualifier

object KarafsAppApiServiceQualifier : Qualifier
object OkHttpQualifier : Qualifier

/**
 * Network level dependencies via Koin DI
 */
val networkModule = module {

    factory(CacheFileQualifier) {
        File(androidContext().cacheDir, CACHE_NAME).apply {
            if (!exists()) mkdir()
        }
    }

    factory { Cache(get(CacheFileQualifier), CACHE_SIZE) }

    factory<Interceptor>(LoggingInterceptorQualifier) {
        HttpLoggingInterceptor { log ->
            Timber.d(log)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory {
        GsonBuilder()
            .setExclusionStrategies(AnnotationExclusionStrategy)
            .disableHtmlEscaping()
            //.registerTypeAdapter()
            .create()
    }

    // This interceptor manages response
    factory(ApiInterceptorQualifier) {
        ApiInterceptor(get())
    }

    single<OkHttpClient>(OkHttpQualifier) {
        OkHttpClient.Builder().apply {
            addInterceptor(get(ApiInterceptorQualifier))
            addInterceptor(get(LoggingInterceptorQualifier))
            cache(get())
            setTimeOuts()
        }
            .build()
    }

    single<Retrofit>(KarafsAppApiServiceQualifier) {
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(get(OkHttpQualifier))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
}

private fun OkHttpClient.Builder.setTimeOuts() {
    val timeout = if (BuildConfig.DEBUG) TIMEOUT_DEBUG else TIMEOUT_RELEASE
    readTimeout(timeout, TimeUnit.SECONDS)
    writeTimeout(timeout, TimeUnit.SECONDS)
    connectTimeout(timeout, TimeUnit.SECONDS)
}