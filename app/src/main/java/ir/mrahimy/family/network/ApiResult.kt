package ir.mrahimy.family.network

import androidx.annotation.StringRes

/**
 * this is a class for separating error responses from success responses
 * Result.Success returns from safeApiCall including data (see network res package or pojo)
 * Result.Error returns from error handler
 */
sealed class ApiResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(@StringRes val stringRes: Int, val errorCode: Int) : ApiResult<Nothing>()

}