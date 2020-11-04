/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.eucalypto.eucalyptapp.sleep.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import de.eucalypto.eucalyptapp.sleep.database.SleepDatabaseDao
import de.eucalypto.eucalyptapp.sleep.database.SleepNight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val tonight = MutableLiveData<SleepNight?>()

    val nights = database.getAllNights()

    val startButtonVisible: LiveData<Boolean> = Transformations.map(tonight) {
        it == null
    }

    val stopButtonVisible: LiveData<Boolean> = Transformations.map(tonight) {
        it != null
    }

    val clearButtonVisible: LiveData<Boolean> = Transformations.map(nights) {
        it.isNotEmpty()
    }

    private val _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: MutableLiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()

            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }

            night
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            Timber.d("onStartTracking called")
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
            Timber.d("got this night: ${tonight.value}")
        }
    }

    private suspend fun insert(newNight: SleepNight) {
        withContext(Dispatchers.IO) {
            Timber.d("insert called")
            database.insert(newNight)
        }
    }

    private val _navigateToSleepQualityInput = MutableLiveData<SleepNight?>()
    val navigateToSleepQualityInput: LiveData<SleepNight?>
        get() = _navigateToSleepQualityInput

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQualityInput.value = oldNight
        }
    }

    fun completeNavigation() {
        _navigateToSleepQualityInput.value = null
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            Timber.d("night being updated in database: $night")
            database.update(night)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
            _showSnackbarEvent.value = true
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            Timber.d("clear() called")
            database.clear()
        }
    }

    private val _navigateToSleepDataDetail = MutableLiveData<Long>()
    val navigateToSleepDataDetail: LiveData<Long>
        get() = _navigateToSleepDataDetail

    fun onSleepNightClicked(nightId: Long) {
        _navigateToSleepDataDetail.value = nightId
    }

    fun onSleepDataDetailNavigated() {
        _navigateToSleepDataDetail.value = null
    }
}

