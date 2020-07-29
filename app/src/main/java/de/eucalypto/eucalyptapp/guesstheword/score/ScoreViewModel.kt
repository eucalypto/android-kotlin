package de.eucalypto.eucalyptapp.guesstheword.score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.eucalypto.eucalyptapp.util.toLiveData
import timber.log.Timber

class ScoreViewModel(finalScore: Int) : ViewModel() {

    private val _score = MutableLiveData<Int>(finalScore)
    val score = _score.toLiveData()

    private val _eventPlayAgain = MutableLiveData<Boolean>(false)
    val eventPlayAgain = _eventPlayAgain.toLiveData()

    init {
        Timber.i("ScoreViewModel created. Final score: $finalScore")
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
        Timber.i("onPlayAgain() executed")
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }
}
