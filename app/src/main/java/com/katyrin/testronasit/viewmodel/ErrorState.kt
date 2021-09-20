package com.katyrin.testronasit.viewmodel

sealed class ErrorState {
    object TimOut: ErrorState()
    object UnknownHost: ErrorState()
    object Connection: ErrorState()
    object Server: ErrorState()
    data class Unknown(val message: String?): ErrorState()
}
