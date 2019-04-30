package pl.sjmprofil.animaltinder.network

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    data class Exception(val exception: kotlin.Exception) : Result<Nothing>()
    data class Respond<out T : Any>(val data: T) : Result<T>()
}