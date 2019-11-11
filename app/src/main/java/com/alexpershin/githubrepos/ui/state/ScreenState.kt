package com.alexpershin.githubrepos.ui.state

import com.alexpershin.githubrepos.R

sealed class ScreenState {
    object EmptyResult : ScreenState()
    object Loading : ScreenState()
    object Success : ScreenState()
    object InternetError : ScreenState()
    class Error(val message: String? = null, val resId: Int = R.string.error_message_unknown_error) : ScreenState()
}