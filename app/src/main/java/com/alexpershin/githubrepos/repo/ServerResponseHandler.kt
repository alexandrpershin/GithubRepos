package com.alexpershin.githubrepos.repo

import com.alexpershin.githubrepos.R
import com.alexpershin.githubrepos.repo.ServerResult.Success
import com.alexpershin.githubrepos.utils.NetworkUtils
import okhttp3.ResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.Response

/**
 * [ServerResponseHandler] is created for handling http responses.
 * */

class ServerResponseHandler<T : Any?> {

    /** [getResult] method returns result depends on [Response.code]
     * if codes is 200,201 -> then return [Success<T>(data = response.body() as T )], see [ServerResult.Success]
     * if code is 500 -> then return [Error(message = response.errorBody().errorMessage)], see [ServerResult.Error]
     * */

    suspend fun getResult(requestAsync: suspend () -> Response<T>): ServerResult<T> {

        if (!NetworkUtils.instance.isNetworkConnected()) {
            return ServerResult.InternetError
        }

        return try {
            val response = requestAsync()

            when (response.code()) {
                200, 201 -> {
                    Success(response.body() as T)
                }
                500 -> handelInternalServerError(response.errorBody())
                502 -> ServerResult.Error(resId = R.string.error_message_bad_gateway)
                else -> {
                    ServerResult.Error(resId = R.string.error_message_unknown_error)
                }
            }
        } catch (ex: Exception) {  // handle all uncaught exceptions
            onError(ex)
        }
    }

    private fun onError(ex: Exception): ServerResult<T> {
        return if (ex is ConnectionShutdownException) {
            ServerResult.InternetError
        } else {
            ServerResult.Error(ex)
        }
    }

    /**
     * Use this method for handling internal server error
     * */

    private fun handelInternalServerError(errorResponseBody: ResponseBody?): ServerResult<T> {
        //It's up to you to parse [errorResponseBody] and get necessary information about error
        // and then return errorCause wrapped with [ServerResult.Error]

        val commonServerError = R.string.error_message_unknown_error

        return ServerResult.Error(resId = commonServerError)
    }

}