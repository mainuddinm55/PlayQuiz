package info.learncoding.playquiz.ui.main

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import info.learncoding.playquiz.R
import info.learncoding.playquiz.data.repository.QuizRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    val score = quizRepository.getHighestScore()

    fun letPlayClicked(view: View) {
        view.findNavController().navigate(R.id.action_main_fragment_to_question_answer_fragment)
    }

}