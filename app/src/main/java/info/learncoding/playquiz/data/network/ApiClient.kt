package info.learncoding.playquiz.data.network

import info.learncoding.playquiz.data.network.response.QuestionResponse
import retrofit2.http.GET

interface ApiClient {

    @GET("quiz.json")
    suspend fun fetchQuiz(): QuestionResponse
}