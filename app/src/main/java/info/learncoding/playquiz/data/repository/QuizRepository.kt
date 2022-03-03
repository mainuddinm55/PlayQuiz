package info.learncoding.playquiz.data.repository

import androidx.lifecycle.LiveData
import info.learncoding.playquiz.data.model.Question
import info.learncoding.playquiz.data.network.response.ApiResponse

interface QuizRepository {

    suspend fun fetchQuizs(): ApiResponse<List<Question>>

    fun getHighestScore(): LiveData<Int>

    fun saveScore(score: Int)

}
