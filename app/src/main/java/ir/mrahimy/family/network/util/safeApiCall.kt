package ir.mrahimy.family.network.util

import ir.mrahimy.family.network.ApiResult
import ir.mrahimy.family.network.error.ApiException
import ir.mrahimy.family.network.error.handleError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * a func to handle network errors
 * Safely Calls the suspend function inside a co-routine context and returns an error if exception occurs
 */
suspend fun <T : Any> safeApiCall(
    call: suspend () -> ApiResult<T>
): ApiResult<T> {
    return withContext(Dispatchers.Main) {
        try {
            withContext(Dispatchers.IO) {
                call()
            }
        } catch (e: Exception) {
            val jsonError = handleError(e)
            val errorCode = if (e is ApiException) e.statusCode else -1
            ApiResult.Error(jsonError, errorCode)
        }
    }
}