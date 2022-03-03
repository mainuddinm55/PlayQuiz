package info.learncoding.playquiz.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import info.learncoding.playquiz.data.local.SharedPref
import info.learncoding.playquiz.data.model.Question
import info.learncoding.playquiz.data.network.ApiClient
import info.learncoding.playquiz.data.network.HandleApiRequest
import info.learncoding.playquiz.data.network.response.ApiResponse

class QuizRepositoryImp(
    private val apiClient: ApiClient,
    private val sharedPref: SharedPref
) : HandleApiRequest(), QuizRepository {

    override suspend fun fetchQuizs(): ApiResponse<List<Question>> {
        return when (val response = makeApiCall { apiClient.fetchQuiz() }) {
            is ApiResponse.Failed -> response
            is ApiResponse.Success -> ApiResponse.Success(response.data.questions)
        }
    }

    override fun getHighestScore(): LiveData<Int> {
        return sharedPref.getScore()
    }

    override fun saveScore(score: Int) {
        sharedPref.saveScore(score)
    }

}