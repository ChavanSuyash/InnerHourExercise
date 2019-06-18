package com.innerhour.exercise.core.network

/**
 * Created by Suyash Chavan.
 */
sealed class Outcome<out T : Any> {
    data class Success<out T : Any>(val data : T) : Outcome<T>()
    data class Error(val exception: Throwable) : Outcome<Nothing>()
}

class NetworkException(override var message: String, var code: Int = 999) : Throwable(message)
class ClientException(override var message: String) : Throwable(message)
