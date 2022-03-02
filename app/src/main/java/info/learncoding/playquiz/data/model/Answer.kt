package info.learncoding.playquiz.data.model

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("A")
    val a: String?,
    @SerializedName("B")
    val b: String?,
    @SerializedName("C")
    val c: String?,
    @SerializedName("D")
    val d: String?
)