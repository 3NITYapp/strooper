package ru.ivos.strooper

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivos.strooper.data.Generator
import ru.ivos.strooper.data.PreferencesRepo
import ru.ivos.strooper.fragments.CountdownFragment
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val preferencesRepo = PreferencesRepo(context)

    private val _timer = MutableLiveData<String>()
    val timer: LiveData<String> get() = _timer

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val _newSetOfColorsAndNames = MutableLiveData<LinkedHashMap<String, Int>>()
    val newSetOfColorsAndNames: LiveData<LinkedHashMap<String, Int>> get() = _newSetOfColorsAndNames

    private val generator = Generator(application)

    private var job: Job? = null

    fun generate(): LinkedHashMap<String, Int> {
        _newSetOfColorsAndNames.value = generator.getMap()
        return generator.getMap()
    }

    fun score(mode: String) {
        when (mode) {
            CountdownFragment.TEXT_TO_TEXT_MODE -> {
                _score.value = _score.value!! + Random.nextInt(85, 95)
            }
            CountdownFragment.TEXT_TO_FIGURE_MODE -> {
                _score.value = _score.value!! + Random.nextInt(90, 100)
            }
            CountdownFragment.FIGURE_TO_TEXT_MODE -> {
                _score.value = _score.value!! + Random.nextInt(85, 90)
            }
            CountdownFragment.MIXED_MODE -> {
                _score.value = _score.value!! + Random.nextInt(95, 105)
            }
        }
        setSharedPref(_score.value!!)
    }

    fun timer() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.Main) {
            val totalSeconds = TimeUnit.MINUTES.toSeconds(1)
            val tickSeconds = 1
            for (second in totalSeconds downTo tickSeconds) {
                val time = String.format("%02d:%02d",
                    TimeUnit.SECONDS.toMinutes(second),
                    second - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(second))
                )
                _timer.value = time
                delay(1000)
            }
            _timer.value = "Done!"
        }
    }

    private fun setSharedPref(scorePref: Int) {
        val oldScore = preferencesRepo.getHighScore()
        if (oldScore < scorePref) {
            preferencesRepo.setHighScore(scorePref.toLong())
        }
    }
}