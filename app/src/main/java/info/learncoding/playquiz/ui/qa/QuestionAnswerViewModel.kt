package info.learncoding.playquiz.ui.qa

import android.os.CountDownTimer
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.learncoding.playquiz.data.DataState
import info.learncoding.playquiz.data.model.Question
import info.learncoding.playquiz.data.network.response.ApiResponse
import info.learncoding.playquiz.data.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionAnswerViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    companion object {
        private const val QUESTION_MAX_APPEAR_TIME = 10000L
        private const val QUESTION_PROGRESS_INTERVAL = 1000L
    }

    private var countDownTimer: CountDownTimer? = null
    var quizCompleteListener: (() -> Unit)? = null
    private val _questions = MutableLiveData<DataState<List<Question>>>()
    val questions: LiveData<DataState<List<Question>>> = _questions
    private val tempQuestions = mutableListOf<Question>()

    val question: ObservableField<Question> = ObservableField()
    val totalScore = ObservableInt(0)

    val answerStrokeColors = ObservableField<Map<String, String>>(hashMapOf())
    val currentIndex = ObservableInt(0)
    val totalQuestion = ObservableField(0)
    val progress = ObservableInt(10)
    val answerEnable = ObservableBoolean(true)

    init {
        fetchQuizs()
    }

    private fun fetchQuizs() {
        viewModelScope.launch {
            _questions.postValue(DataState.Loading())
            when (val response = quizRepository.fetchQuizs()) {
                is ApiResponse.Failed -> _questions.postValue(DataState.Failed(response.message))
                is ApiResponse.Success -> {
                    _questions.postValue(DataState.Loaded(response.data))
                    tempQuestions.clear()
                    tempQuestions.addAll(response.data)
                    totalQuestion.set(tempQuestions.size)
                    updateNextQuestion(true)
                }
            }
        }
    }

    fun onAnswerAClicked(view: View) {
        if ("A" == question.get()?.correctAnswer) {
            updateScore(question.get()?.score)
            updateStrokeColor("A", true)
        } else {
            updateStrokeColor("A", false)
        }
        updateNextQuestion()
    }

    fun onAnswerBClicked(view: View) {
        if ("B" == question.get()?.correctAnswer) {
            updateScore(question.get()?.score)
            updateStrokeColor("B", true)
        } else {
            updateStrokeColor("B", false)
        }
        updateNextQuestion()
    }

    fun onAnswerCClicked(view: View) {
        if ("C" == question.get()?.correctAnswer) {
            updateScore(question.get()?.score)
            updateStrokeColor("C", true)
        } else {
            updateStrokeColor("C", false)
        }
        updateNextQuestion()
    }

    fun onAnswerDClicked(view: View) {
        if ("D" == question.get()?.correctAnswer) {
            updateScore(question.get()?.score)
            updateStrokeColor("D", true)
        } else {
            updateStrokeColor("D", false)
        }
        updateNextQuestion()
    }

    private fun updateScore(score: Int?) {
        totalScore.set(totalScore.get().plus(score ?: 0))
    }

    private fun updateStrokeColor(answer: String, isCorrect: Boolean) {
        answerStrokeColors.set(hashMapOf<String, String>().apply {
            if (isCorrect) {
                put(answer, "#FF388E3C")
            } else {
                put(question.get()?.correctAnswer ?: return, "#FF388E3C")
                put(answer, "#FFD32F2F")
            }
        })
    }

    private fun updateNextQuestion(isFirst: Boolean = false) {
        countDownTimer?.cancel()
        viewModelScope.launch(Dispatchers.Main) {
            answerEnable.set(false)
            if (!isFirst) {
                delay(2000L)
                progress.set(10)
                currentIndex.set(currentIndex.get() + 1)
            }
            if (tempQuestions.size > currentIndex.get()) {
                question.set(tempQuestions[currentIndex.get()])
                answerStrokeColors.set(hashMapOf())
                answerEnable.set(true)
                startAnswerTimer()
            } else {
                quizCompleteListener?.invoke()
            }
        }
    }


    private fun startAnswerTimer() {
        countDownTimer = object : CountDownTimer(
            QUESTION_MAX_APPEAR_TIME, QUESTION_PROGRESS_INTERVAL
        ) {
            override fun onTick(timestamp: Long) {
                progress.set((timestamp / 1000).toInt())
            }

            override fun onFinish() {
                countDownTimer?.cancel()
                updateNextQuestion()
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }
}