package info.learncoding.playquiz.ui.qa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.learncoding.playquiz.R
import info.learncoding.playquiz.databinding.FragmentQuestionAnswerBinding
import info.learncoding.playquiz.ui.base.BaseFragment

@AndroidEntryPoint
class QuestionAnswerFragment :
    BaseFragment<QuestionAnswerViewModel, FragmentQuestionAnswerBinding>() {

    override val viewModel: QuestionAnswerViewModel by viewModels()

    override val layout: Int = R.layout.fragment_question_answer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.viewModel = viewModel
        viewModel.quizCompleteListener = {
            findNavController().popBackStack()
        }
        viewModel.questions.observe(viewLifecycleOwner) {
            Log.d(this::class.java.simpleName, "onViewCreated: $it")
        }
    }
}