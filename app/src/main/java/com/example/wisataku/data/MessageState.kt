package com.example.wisataku.data

sealed class MessageState<out R> private constructor() {
    data class Success<out T>(val data: T) : MessageState<T>()
    data class Error(val error: String) : MessageState<Nothing>()
    object Loading : MessageState<Nothing>()
}