package info.learncoding.playquiz.ui.qa

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.learncoding.playquiz.R
import info.learncoding.playquiz.data.DataState
import info.learncoding.playquiz.databinding.FragmentQuestionAnswerBinding
import info.learncoding.playquiz.ui.base.BaseFragment
import info.learncoding.playquiz.utils.hide
import info.learncoding.playquiz.utils.show
import info.learncoding.playquiz.utils.showToast

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

        observeQuizQuestions()

    }

    private fun observeQuizQuestions() {
        viewModel.questions.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Failed -> {
                    showProgress(false)
                    showToast(it.message)
                }
                is DataState.Loaded -> {
                    showProgress(false)
                    binding?.dataLayout?.show()
                }
                is DataState.Loading -> showProgress()
            }
        }
    }

    private fun showProgress(isShow: Boolean = true) {
        if (isShow) {
            binding?.progressBar?.show()
        } else {
            binding?.progressBar?.hide()
        }
    }
}