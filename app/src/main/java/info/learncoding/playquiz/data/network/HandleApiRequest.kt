package info.learncoding.playquiz.data.network

import info.learncoding.playquiz.data.network.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

open class HandleApiRequest {
    suspend fun <T> makeApiCall(apiCall: suspend () -> T): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResponse.Success(apiCall.invoke())
            } catch (e: Exception) {
                var message = "Something went wrong, please try again"
                e.printStackTrace()
                when (e) {
                    is IOException -> {
                        message = e.message ?: "No internet connection"
                    }
                    is HttpException -> {
                        when (e.code()) {
                            400 -> {
                                message = "Invalid request, please try again"
                            }
                            204 -> {
                                message = "No Data Found"
                            }
                            401 -> {
                                message = "Unauthorized"
                            }
                            403 -> {
                                message = "Forbidden"
                            }
                            404 -> {
                                message = "Request not found"
                            }
                            422 -> {
                                message = "Missing required field"
                            }
                            500 -> {
                                message = "Internal server error"
                            }
                            else -> {
                                message = e.message()
                            }
                        }
                    }
                }
                ApiResponse.Failed(message)

            }
        }
    }
}