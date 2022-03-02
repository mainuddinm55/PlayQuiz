package info.learncoding.playquiz.data.network.response

sealed class ApiResponse<out T> {

    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class Failed(
        val message: String = "Something went wrong, Please try again"
    ) : ApiResponse<Nothing>()

}