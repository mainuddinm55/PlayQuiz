package info.learncoding.playquiz.data.model

data class Question(
    val question: String?,
    val answers: Answer?,
    val questionImageUrl: String?,
    val correctAnswer: String?,
    val score: Int?
)

