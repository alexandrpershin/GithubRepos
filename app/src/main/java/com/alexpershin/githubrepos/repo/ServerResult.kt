package com.alexpershin.githubrepos.repo

sealed class ServerResult<out T : Any?> {
    class Success<T : Any?>(val data: T) : ServerResult<T>()
    class Error(val exception: Throwable? = null, val message: String? = null, val resId : Int = 0) : ServerResult<Nothing>()
    object InternetError : ServerResult<Nothing>()
}