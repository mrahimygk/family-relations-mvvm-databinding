package ir.mrahimy.family.network.error

import java.io.IOException

/**
 * Custom Exception class for handling server errors
 */
class ApiException(val statusCode: Int, e: Throwable? = null) : IOException(e)