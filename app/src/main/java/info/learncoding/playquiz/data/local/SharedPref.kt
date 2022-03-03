package info.learncoding.playquiz.data.local

import androidx.lifecycle.LiveData

interface SharedPref {
    fun saveScore(score: Int)
    fun getScore(): LiveData<Int>
}