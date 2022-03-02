package info.learncoding.playquiz.data.network.response

import info.learncoding.playquiz.data.model.Question

data class QuestionResponse(
    val questions: List<Question>
)