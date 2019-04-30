package pl.sjmprofil.animaltinder.network

import retrofit2.Response
import java.lang.Exception

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): pl.sjmprofil.animaltinder.network.Result<T> {

    return try {
        val response = call.invoke()

        if (response.isSuccessful) Result.Success(response.body()!!)
        else Result.Error(response.errorBody().toString())
    } catch (e: Exception) {
        Result.Exception(e)
    }
}