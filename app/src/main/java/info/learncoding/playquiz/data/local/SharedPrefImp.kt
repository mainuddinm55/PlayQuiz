package info.learncoding.playquiz.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.log

class SharedPrefImp(context: Context) : SharedPref {
    companion object {
        private const val KEY_HIGH_SCORE = "high_score"
    }

    private val _highScore = MutableLiveData(0)
    private val sharedPref = context.getSharedPreferences("score_pref", Context.MODE_PRIVATE)

    init {
        _highScore.postValue(sharedPref.getInt(KEY_HIGH_SCORE, 0))
    }

    override fun saveScore(score: Int) {
        if (score > _highScore.value ?: 0) {
            sharedPref.edit().putInt(KEY_HIGH_SCORE, score).apply()
            _highScore.postValue(score)
        }
        Log.d(this::class.java.simpleName, "saveScore: saved")
    }

    override fun getScore(): LiveData<Int> {
        return _highScore
    }

}